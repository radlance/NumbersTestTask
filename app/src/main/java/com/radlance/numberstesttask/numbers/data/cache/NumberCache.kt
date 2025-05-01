package com.radlance.numberstesttask.numbers.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "number")
data class NumberCache(
    @PrimaryKey val number: String,
    val fact: String,
    val date: Long
)
