package com.radlance.numberstesttask.numbers.data

import com.radlance.numberstesttask.numbers.data.cache.NumbersCacheDataSource
import com.radlance.numberstesttask.numbers.domain.HandleError
import com.radlance.numberstesttask.numbers.domain.NumberFact

interface HandleDataRequest {

    suspend fun handle(block: suspend () -> NumberData): NumberFact

    class Base(
        private val cacheDataSource: NumbersCacheDataSource,
        private val handleError: HandleError<Exception>,
        private val mapperToDomain: NumberData.Mapper<NumberFact>
    ) : HandleDataRequest {
        override suspend fun handle(block: suspend () -> NumberData): NumberFact {
            return try {
                val fact = block.invoke()
                cacheDataSource.saveNumber(fact)
                fact.map(mapperToDomain)
            } catch (e: Exception) {
                throw handleError.handle(e)
            }
        }
    }
}