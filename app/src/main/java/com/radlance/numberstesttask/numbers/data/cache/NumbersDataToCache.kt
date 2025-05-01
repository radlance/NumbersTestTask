package com.radlance.numberstesttask.numbers.data.cache

import com.radlance.numberstesttask.numbers.data.NumberData

class NumbersDataToCache : NumberData.Mapper<NumberCache> {
    override fun map(id: String, fact: String): NumberCache {
        return NumberCache(id, fact, System.currentTimeMillis())
    }
}