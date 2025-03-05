package com.radlance.numberstesttask.numbers.domain

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NumbersInteractorTest {
    private lateinit var repository: TestNumbersRepository
    private lateinit var interactor: NumbersInteractor

    @Before
    fun setup() {
        repository = TestNumbersRepository()
        interactor = NumbersInteractor.Base(repository)
    }

    @Test
    fun test_init_success() = runTest {
        repository.changeExpectedList(listOf(NumberFact(id = "6", fact = "fact about 6")))

        val actual = interactor.init()
        val expected = NumbersResult.Success(listOf(NumberFact(id = "6", fact = "fact about 6")))

        assertEquals(expected, actual)
        assertEquals(1, repository.numberFactCalledList.size)
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
        assertEquals(1, repository.numberFactCalledList)
    }

    @Test
    fun test_fact_about_number_error() = runTest {
        repository.expectingErrorGetFact(error = true)

        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Failure(message = "no internet connection")

        assertEquals(expected, actual)
        assertEquals("7", repository.numberFactCalledList[0])
        assertEquals(1, repository.numberFactCalledList)
    }

    @Test
    fun test_fact_about_random_number_success() = runTest {
        repository.changeExpectedFactOfRandomNumber(
            numberFact = NumberFact(id = "7", fact = "fact about 7")
        )

        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Success(listOf(NumberFact(id = "7", fact = "fact about 7")))

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumberFactCalledList)
    }

    @Test
    fun test_fact_about_random_number_error() = runTest {
        repository.expectingErrorGetRandomFact(error = true)

        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Failure(message = "no internet connection")

        assertEquals(expected, actual)
        assertEquals("7", repository.randomNumberFactCalledList[0])
        assertEquals(1, repository.randomNumberFactCalledList)
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

        override fun allNumbers(): List<NumberFact> {
            allNumbersCalledCount++
            return allNumbers
        }

        override fun factAboutNumber(number: String): NumberFact {
            numberFactCalledList.add(number)
            if (errorWhileNumberFact) {
                throw NoInternetConectionException()
            }
            return numberFact
        }

        override fun randomNumberFact(): NumberFact {
            randomNumberFactCalledList.add("")
            if (errorWhileNumberFact) {
                throw NoInternetConectionException()
            }
            return numberFact
        }
    }
}