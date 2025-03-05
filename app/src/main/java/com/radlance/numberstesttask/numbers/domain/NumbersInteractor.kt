package com.radlance.numberstesttask.numbers.domain

interface NumbersInteractor {

    suspend fun init(): NumbersResult

    suspend fun factAboutNumber(number: String): NumbersResult

    suspend fun factAboutRandomNumber(): NumbersResult

    class Base(
        private val repository: NumbersRepository,
        private val handleRequest: HandleRequest
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
    }
}