package com.radlance.numberstesttask.numbers.data.cloud

import com.radlance.numberstesttask.numbers.data.FetchNumber
import com.radlance.numberstesttask.numbers.data.NumberData

interface NumbersCloudDataSource : FetchNumber {

    suspend fun randomNumber(): NumberData

    class Base(
        private val service: NumbersService,
        private val randomApiHeader: RandomApiHeader
    ) : NumbersCloudDataSource {

        override suspend fun randomNumber(): NumberData {
            val response = service.random()
            val body = response.body() ?: throw IllegalStateException("service unavailable")
            val headers = response.headers()

            randomApiHeader.findInHeaders(headers)?.let { (_, value) ->
                return NumberData(value, body)
            }

            throw IllegalStateException("service unavailable")
        }

        override suspend fun number(number: String): NumberData {
            val fact = service.fact(number)

            return NumberData(id = number, fact = fact)
        }
    }
}