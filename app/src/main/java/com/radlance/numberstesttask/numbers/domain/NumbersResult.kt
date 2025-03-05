package com.radlance.numberstesttask.numbers.domain

interface NumbersResult {
    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {
        fun mapSuccess(list: List<NumberFact>): T
        fun mapError(message: String): T
    }

    data class Success(private val list: List<NumberFact> = emptyList()) : NumbersResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapSuccess(list)
        }
    }

    data class Failure(private val message: String) : NumbersResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapError(message)
        }
    }
}