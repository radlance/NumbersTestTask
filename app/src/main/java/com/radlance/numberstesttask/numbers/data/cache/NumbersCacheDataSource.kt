package com.radlance.numberstesttask.numbers.data.cache

import com.radlance.numberstesttask.numbers.data.FetchNumber
import com.radlance.numberstesttask.numbers.data.NumberData

interface NumbersCacheDataSource : FetchNumber {

    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumberFact(numberData: NumberData)
}