package com.radlance.numberstesttask.numbers.data

import com.radlance.numberstesttask.numbers.data.cache.NumberCache
import com.radlance.numberstesttask.numbers.data.cache.NumbersCacheDataSource
import com.radlance.numberstesttask.numbers.data.cache.NumbersDao
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NumbersCacheDataSourceTest {

    private lateinit var dataSource: NumbersCacheDataSource
    private lateinit var dao: TestDao

    @Before
    fun setup() {
        dao = TestDao()
        dataSource = NumbersCacheDataSource.Base(
            dao = dao,
            dataToCache = TestMapper(date = 5)
        )
    }

    @Test
    fun test_all_numbers_empty() = runTest {
        val actualList = dataSource.allNumbers()
        val expectedList = emptyList<NumberData>()

        assertEquals(expectedList, actualList)
    }

    @Test
    fun test_all_numbers_not_empty() = runTest {
        dao.data.add(NumberCache(number = "1", fact = "fact1", date = 1))
        dao.data.add(NumberCache(number = "2", fact = "fact2", date = 2))

        val actualList = dataSource.allNumbers()
        val expectedList = listOf(
            NumberData(id = "1", fact = "fact1"),
            NumberData(id = "2", fact = "fact2")
        )

        assertEquals(expectedList, actualList)
    }

    @Test
    fun test_contains() = runTest {
        dao.data.add(NumberCache(number = "5", fact = "fact5", date = 5))
        assertTrue(dataSource.contains(number = "5"))
    }

    @Test
    fun test_not_contains() = runTest {
        dao.data.add(NumberCache(number = "0", fact = "fact0", date = 0))
        assertFalse(dataSource.contains(number = "5"))
    }

    @Test
    fun test_save_number() = runTest {
        dataSource.saveNumber(NumberData(id = "4", fact = "fact4"))

        val actualNumber = dao.data.first()
        val expectedNumber = NumberCache(number = "4", fact = "fact4", date = 5)

        assertEquals(expectedNumber, actualNumber)
    }

    @Test
    fun test_number_contains() = runTest {
        dao.data.add(NumberCache(number = "8", fact = "fact8", date = 8))

        val actual = dataSource.number("8")
        val expected = NumberData(id = "8", fact = "fact8")

        assertEquals(expected, actual)
    }

    @Test
    fun test_number_not_contains() = runTest {
        dao.data.add(NumberCache(number = "8", fact = "fact8", date = 8))

        val actual = dataSource.number("9")
        val expected = NumberData(id = "", fact = "")

        assertEquals(expected, actual)
    }

    private class TestDao : NumbersDao {

        val data = mutableListOf<NumberCache>()

        override suspend fun allNumbers(): List<NumberCache> = data

        override suspend fun insert(number: NumberCache) {
            data.add(number)
        }

        override suspend fun number(number: String): NumberCache? {
            return data.find { it.number == number }
        }
    }

    class TestMapper(private val date: Long) : NumberData.Mapper<NumberCache> {
        override fun map(id: String, fact: String): NumberCache {
            return NumberCache(number = id, fact = fact, date = date)
        }
    }
}