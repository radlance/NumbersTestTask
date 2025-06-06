package com.radlance.numberstesttask.numbers.data

import com.radlance.numberstesttask.numbers.data.cache.NumbersCacheDataSource
import com.radlance.numberstesttask.numbers.data.cloud.NumbersCloudDataSource
import com.radlance.numberstesttask.numbers.domain.NoInternetConnectionException
import com.radlance.numberstesttask.numbers.domain.NumberFact
import com.radlance.numberstesttask.numbers.domain.NumbersRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseNumbersRepositoryTest {

    private lateinit var cloudDataSource: TestNumbersCloudDataSource
    private lateinit var cacheDataSource: TestNumbersCacheDataSource

    private lateinit var repository: NumbersRepository

    @Before
    fun setup() {
        cloudDataSource = TestNumbersCloudDataSource()
        cacheDataSource = TestNumbersCacheDataSource()
        val mapper = NumberDataToDomain()

        repository = BaseNumbersRepository(
            cloudDataSource = cloudDataSource,
            cacheDataSource = cacheDataSource,
            handleDataRequest = HandleDataRequest.Base(
                cacheDataSource = cacheDataSource,
                handleError = HandleDomainError(),
                mapperToDomain = mapper
            ),
            mapperToDomain = mapper
        )
    }

    @Test
    fun test_all_numbers() = runTest {
        cacheDataSource.replaceData(
            listOf(
                NumberData("4", "fact of 4"),
                NumberData("5", "fact of 5")
            )
        )

        val actual = repository.allNumbers()
        val expected = listOf(
            NumberFact("4", "fact of 4"),
            NumberFact("5", "fact of 5")
        )

        assertEquals(expected, actual)
        assertEquals(1, cacheDataSource.allNumbersCalledCount)
    }

    @Test
    fun test_number_fact_not_cached_success() = runTest {
        cloudDataSource.makeExpected(NumberData("10", "fact about 10"))
        cacheDataSource.replaceData(emptyList())

        val actual = repository.numberFact("10")
        val expected = NumberFact("10", "fact about 10")
        assertEquals(expected, actual)

        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(false, cacheDataSource.containsCalledList.first())
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(1, cloudDataSource.numberFactCalledList.size)
        assertEquals("10", cloudDataSource.numberFactCalledList.first())
        assertEquals(NumberData("10", "fact about 10"), cacheDataSource.data.first())
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test(expected = NoInternetConnectionException::class)
    fun test_number_fact_not_cached_failure() = runTest {
        cloudDataSource.changeConnection(connected = false)
        cacheDataSource.replaceData(emptyList())

        repository.numberFact("10")
    }

    @Test
    fun test_number_fact_cached() = runTest {
        cloudDataSource.changeConnection(connected = true)
        cloudDataSource.makeExpected(NumberData("10", "cloud 10"))
        cacheDataSource.replaceData(listOf(NumberData("10", "fact 10")))

        val actual = repository.numberFact("10")
        val expected = NumberFact("10", "fact 10")

        assertEquals(expected, actual)
        assertEquals(1, cacheDataSource.numberFactCalledList.size)
        assertEquals("10", cacheDataSource.numberFactCalledList.first())
        assertEquals(0, cloudDataSource.numberFactCalledList.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(NumberData("10", "fact 10"), cacheDataSource.data.first())
    }

    @Test
    fun test_random_number_fact_not_cached_success() = runTest {
        cloudDataSource.makeExpected(NumberData("10", "fact about 10"))
        cacheDataSource.replaceData(emptyList())

        val actual = repository.randomNumberFact()
        val expected = NumberFact("10", "fact about 10")

        assertEquals(expected, actual)
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(0, cloudDataSource.numberFactCalledList.size)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(NumberData("10", "fact about 10"), cacheDataSource.data.first())
    }

    @Test(expected = NoInternetConnectionException::class)
    fun test_random_number_fact_not_cached_failure() = runTest {
        cloudDataSource.changeConnection(connected = false)
        cacheDataSource.replaceData(emptyList())

        repository.randomNumberFact()
    }

    @Test
    fun test_random_number_fact_cached() = runTest {
        cloudDataSource.changeConnection(connected = true)
        cloudDataSource.makeExpected(NumberData("10", "cloud 10"))
        cacheDataSource.replaceData(listOf(NumberData("10", "fact 10")))

        val actual = repository.randomNumberFact()
        val expected = NumberFact("10", "cloud 10")

        assertEquals(expected, actual)
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
    }

    private class TestNumbersCloudDataSource : NumbersCloudDataSource {

        var thereIsConnection = true
        var numberData = NumberData("", "")
        val numberFactCalledList = mutableListOf<String>()
        var randomNumberFactCalledCount = 0

        fun changeConnection(connected: Boolean) {
            thereIsConnection = connected
        }

        fun makeExpected(number: NumberData) {
            numberData = number
        }

        override suspend fun number(number: String): NumberData {
            numberFactCalledList.add(number)
            return if (thereIsConnection) {
                numberData
            } else {
                throw UnknownHostException()
            }
        }

        override suspend fun randomNumber(): NumberData {
            randomNumberFactCalledCount++

            return if (thereIsConnection) {
                numberData
            } else {
                throw UnknownHostException()
            }
        }
    }

    private class TestNumbersCacheDataSource : NumbersCacheDataSource {

        val containsCalledList = mutableListOf<Boolean>()
        val numberFactCalledList = mutableListOf<String>()
        var allNumbersCalledCount = 0
        var saveNumberFactCalledCount = 0
        val data = mutableListOf<NumberData>()

        fun replaceData(newData: List<NumberData>) {
            data.clear()
            data.addAll(newData)
        }

        override suspend fun allNumbers(): List<NumberData> {
            allNumbersCalledCount++
            return data
        }

        override suspend fun contains(number: String): Boolean {
            val result = data.find { it.map(NumberData.Mapper.Matches(number)) } != null
            containsCalledList.add(result)
            return result
        }

        override suspend fun number(number: String): NumberData {
            numberFactCalledList.add(number)
            return data.first()
        }

        override suspend fun saveNumber(numberData: NumberData) {
            data.add(numberData)
            saveNumberFactCalledCount++
        }
    }
}