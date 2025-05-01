package com.radlance.numberstesttask.numbers.data.cache

import android.content.Context
import androidx.room.Room

interface CacheModule {

    fun provideDatabase(): NumbersDatabase

    class Base(private val context: Context) : CacheModule {

        private val database by lazy {
            Room.databaseBuilder(
                context.applicationContext,
                NumbersDatabase::class.java,
                "numbers_db"
            ).build()
        }

        override fun provideDatabase(): NumbersDatabase = database

    }
}