package com.radlance.numberstesttask.numbers.domain

interface HandleRequest {
    suspend fun handle(action: suspend () -> Unit): NumbersResult

    class Base(
        private val handleError: HandleError,
        private val repository: NumbersRepository
    ) : HandleRequest {
        override suspend fun handle(action: suspend () -> Unit): NumbersResult {
            return try {
                action.invoke()
                NumbersResult.Success(repository.allNumbers())
            } catch (e: Exception) {
                NumbersResult.Failure(handleError.handle(e))
            }
        }
    }
}