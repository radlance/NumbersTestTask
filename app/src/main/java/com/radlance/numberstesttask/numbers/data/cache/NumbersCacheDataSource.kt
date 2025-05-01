package com.radlance.numberstesttask.numbers.data.cache

import com.radlance.numberstesttask.numbers.data.FetchNumber
import com.radlance.numberstesttask.numbers.data.NumberData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface NumbersCacheDataSource : FetchNumber {

    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumber(numberData: NumberData)

    class Base(
        private val dao: NumbersDao,
        private val dataToCache: NumberData.Mapper<NumberCache>
    ) : NumbersCacheDataSource {

        private val mutex = Mutex()

        override suspend fun number(number: String): NumberData = mutex.withLock {
            val numberCache = dao.number(number) ?: NumberCache(number = "", fact = "", date = 0)
            return NumberData(id = numberCache.number, fact = numberCache.fact)
        }

        override suspend fun allNumbers(): List<NumberData> = mutex.withLock {
            val data = dao.allNumbers()
            return data.map { NumberData(it.number, it.fact) }
        }

        override suspend fun contains(number: String): Boolean = mutex.withLock {
            val data = dao.number(number)
            return data != null
        }

        override suspend fun saveNumber(numberData: NumberData) = mutex.withLock {
            dao.insert(numberData.map(dataToCache))
        }
    }
}