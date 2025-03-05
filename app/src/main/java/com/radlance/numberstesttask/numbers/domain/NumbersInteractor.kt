package com.radlance.numberstesttask.numbers.domain

interface NumbersInteractor {
    suspend fun init(): NumbersResult

    suspend fun factAboutNumber(number: String): NumbersResult

    suspend fun factAboutRandomNumber(): NumbersResult
}