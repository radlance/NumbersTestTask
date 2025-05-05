package com.radlance.numberstesttask.numbers.data.cloud

import com.radlance.numberstesttask.numbers.data.NumberData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NumberCloudDataSourceTest {
    private lateinit var dataSource: NumbersCloudDataSource

    @Before
    fun setup() {
        val randomApiHeader = RandomApiHeader.Mock("test")
        dataSource = NumbersCloudDataSource.Base(
            randomApiHeader = randomApiHeader,
            service = MockNumbersService(randomApiHeader)
        )
    }

    @Test
    fun test_number() = runTest {
        val actual = dataSource.number("1")
        val expected = NumberData("1", "fact about 1")
        assertEquals(expected, actual)
    }

    @Test
    fun test_random_success() = runTest {
        val actual = dataSource.randomNumber()
        val expected = NumberData("1", "fact about 1")
        assertEquals(expected, actual)
    }

    @Test(expected = IllegalStateException::class)
    fun test_random_failed() = runTest {
        val apiHeader = RandomApiHeader.Mock("test")
        val service = MockNumbersService(apiHeader)
        val emptyHeader = RandomApiHeader.Mock("")
        val dataSource = NumbersCloudDataSource.Base(service, emptyHeader)

        dataSource.randomNumber()
    }
}