package com.radlance.numberstesttask

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.radlance.numberstesttask.numbers.data.cache.NumberCache
import com.radlance.numberstesttask.numbers.data.cache.NumbersDao
import com.radlance.numberstesttask.numbers.data.cache.NumbersDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: NumbersDatabase
    private lateinit var dao: NumbersDao

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, NumbersDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        dao = db.numbersDao()
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        db.close()
    }

    @Test
    fun test_add_and_check() = runBlocking {
        assertEquals(emptyList<NumberCache>(), dao.allNumbers())

        val number = NumberCache(number = "42", fact = "fact42", date = 12)
        dao.insert(number)

        val actualList = dao.allNumbers()
        assertEquals(number, actualList.first())
        val actual = dao.number("42")
        assertEquals(number, actual)
    }

    @Test
    fun test_add_two_times() = runBlocking {
        assertEquals(emptyList<NumberCache>(), dao.allNumbers())

        val number = NumberCache(number = "42", fact = "fact42", date = 12)
        dao.insert(number)
        var actualList = dao.allNumbers()
        assertEquals(number, actualList.first())

        val new = NumberCache(number = "42", fact = "fact42", date = 15)
        dao.insert(new)
        actualList = dao.allNumbers()
        assertEquals(1, actualList.size)
        assertEquals(new, actualList.first())
    }
}