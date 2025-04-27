package com.radlance.numberstesttask.numbers.data

interface FetchNumber {

    suspend fun number(number: String): NumberData
}