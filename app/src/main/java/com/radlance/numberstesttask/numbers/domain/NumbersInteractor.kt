package com.radlance.numberstesttask.numbers.domain

import com.radlance.numberstesttask.details.data.NumberFactDetails

interface NumbersInteractor :
    NumbersInitialUseCase,
    NumbersFactUseCase,
    RandomNumbersUseCase,
    NumberDetailsUseCase {

    class Base(
        private val repository: NumbersRepository,
        private val handleRequest: HandleRequest,
        private val numberFactDetails: NumberFactDetails.Save
    ) : NumbersInteractor {
        override suspend fun init(): NumbersResult {
            return NumbersResult.Success(repository.allNumbers())
        }

        override suspend fun factAboutNumber(number: String): NumbersResult {
            return handleRequest.handle { repository.numberFact(number) }
        }

        override suspend fun factAboutRandomNumber(): NumbersResult {
            return handleRequest.handle { repository.randomNumberFact() }
        }

        override fun saveDetails(details: String) {
            numberFactDetails.save(details)
        }
    }
}

interface NumbersInitialUseCase {

    suspend fun init(): NumbersResult
}

interface NumbersFactUseCase {

    suspend fun factAboutNumber(number: String): NumbersResult
}

interface RandomNumbersUseCase {

    suspend fun factAboutRandomNumber(): NumbersResult
}

interface NumberDetailsUseCase {

    fun saveDetails(details: String)
}