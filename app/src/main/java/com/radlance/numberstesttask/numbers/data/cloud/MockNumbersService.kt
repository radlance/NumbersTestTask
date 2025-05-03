package com.radlance.numberstesttask.numbers.data.cloud

import retrofit2.Response

class MockNumbersService : NumbersService {
    override suspend fun fact(id: String): String = "fact about $id"

    override suspend fun random(): Response<String> {
        TODO()
    }
}