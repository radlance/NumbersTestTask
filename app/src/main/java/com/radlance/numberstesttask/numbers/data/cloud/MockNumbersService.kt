package com.radlance.numberstesttask.numbers.data.cloud

import okhttp3.Headers.Companion.toHeaders
import retrofit2.Response

class MockNumbersService : NumbersService {

    private var count = 0

    override suspend fun fact(id: String): String = "fact about $id"

    override suspend fun random(): Response<String> {
        count++
        return Response.success(
            "fact about $count",
            mapOf("X-Numbers-API-Number" to count.toString()).toHeaders()
        )
    }
}