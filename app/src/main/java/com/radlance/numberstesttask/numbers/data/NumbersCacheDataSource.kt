package com.radlance.numberstesttask.numbers.data

interface NumbersCacheDataSource : FetchNumber {

    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumberFact(numberData: NumberData)
}