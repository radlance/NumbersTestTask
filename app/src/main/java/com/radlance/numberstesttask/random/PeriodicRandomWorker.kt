package com.radlance.numberstesttask.random

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.radlance.numberstesttask.numbers.domain.RandomNumberRepository

class PeriodicRandomWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return try {
            val repository = (applicationContext as ProvidePeriodicRepository).providePeriodicRepository()
            repository.randomNumberFact()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

interface ProvidePeriodicRepository {
    fun providePeriodicRepository(): RandomNumberRepository
}