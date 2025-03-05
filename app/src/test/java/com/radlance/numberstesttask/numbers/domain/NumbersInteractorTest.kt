package com.radlance.numberstesttask.numbers.domain

import com.radlance.numberstesttask.common.BaseTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NumbersInteractorTest : BaseTest() {
    private lateinit var repository: TestNumbersRepository
    private lateinit var interactor: NumbersInteractor
    private lateinit var manageResources: TestManageResources

    @Before
    fun setup() {
        manageResources = TestManageResources()
        repository = TestNumbersRepository()
        interactor = NumbersInteractor.Base(
            repository = repository,
            handleRequest = HandleRequest.Base(HandleError.Base(manageResources), repository)
        )
    }

    @Test
    fun test_init_success() = runTest {
        repository.changeExpectedList(listOf(NumberFact(id = "6", fact = "fact about 6")))

        val actual = interactor.init()
        val expected = NumbersResult.Success(listOf(NumberFact(id = "6", fact = "fact about 6")))

        assertEquals(expected, actual)
        assertEquals(1, repository.allNumbersCalledCount)
    }

    @Test
    fun test_fact_about_number_success() = runTest {
        repository.changeExpectedFactOfNumber(
            numberFact = NumberFact(id = "7", fact = "fact about 7")
        )

        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Success(listOf(NumberFact(id = "7", fact = "fact about 7")))

        assertEquals(expected, actual)
        assertEquals("7", repository.numberFactCalledList[0])
        assertEquals(1, repository.numberFactCalledList.size)
    }

    @Test
    fun test_fact_about_number_error() = runTest {
        repository.expectingErrorGetFact(error = true)
        manageResources.makeExpectedAnswer("no internet connection")

        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Failure(message = "no internet connection")

        assertEquals(expected, actual)
        assertEquals(1, repository.numberFactCalledList.size)
    }

    @Test
    fun test_fact_about_random_number_success() = runTest {
        repository.changeExpectedFactOfRandomNumber(
            numberFact = NumberFact(id = "7", fact = "fact about 7")
        )

        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Success(listOf(NumberFact(id = "7", fact = "fact about 7")))

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumberFactCalledList.size)
    }

    @Test
    fun test_fact_about_random_number_error() = runTest {
        repository.expectingErrorGetRandomFact(error = true)
        manageResources.makeExpectedAnswer("no internet connection")

        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Failure(message = "no internet connection")

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumberFactCalledList.size)
    }

    private class TestNumbersRepository : NumbersRepository {
        private val allNumbers = mutableListOf<NumberFact>()
        private var numberFact = NumberFact(id = "", fact = "")

        var errorWhileNumberFact = false

        var allNumbersCalledCount = 0
        var numberFactCalledList = mutableListOf<String>()
        var randomNumberFactCalledList = mutableListOf<String>()

        fun changeExpectedList(list: List<NumberFact>) {
            allNumbers.clear()
            allNumbers.addAll(list)
        }

        fun changeExpectedFactOfRandomNumber(numberFact: NumberFact) {
            this.numberFact = numberFact
        }


        fun changeExpectedFactOfNumber(numberFact: NumberFact) {
            this.numberFact = numberFact
        }

        fun expectingErrorGetFact(error: Boolean) {
            errorWhileNumberFact = error
        }

        fun expectingErrorGetRandomFact(error: Boolean) {
            errorWhileNumberFact = error
        }

        override suspend fun allNumbers(): List<NumberFact> {
            allNumbersCalledCount++
            return allNumbers
        }

        override suspend fun numberFact(number: String): NumberFact {
            numberFactCalledList.add(number)
            allNumbers.add(numberFact)
            if (errorWhileNumberFact) {
                throw NoInternetConnectionException()
            }
            return numberFact
        }

        override suspend fun randomNumberFact(): NumberFact {
            randomNumberFactCalledList.add("")
            allNumbers.add(numberFact)
            if (errorWhileNumberFact) {
                throw NoInternetConnectionException()
            }
            return numberFact
        }
    }
}