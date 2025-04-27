package com.radlance.numberstesttask.numbers.data

interface NumbersCloudDataSource : FetchNumber {

    suspend fun randomNumber(): NumberData
}