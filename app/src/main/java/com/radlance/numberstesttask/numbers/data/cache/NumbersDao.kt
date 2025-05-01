package com.radlance.numberstesttask.numbers.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NumbersDao {

    @Query("SELECT * FROM number ORDER BY fact ASC")
    suspend fun allNumbers(): List<NumberCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(number: NumberCache)

    @Query("SELECT * FROM number WHERE number = :number")
    suspend fun number(number: String): NumberCache?
}