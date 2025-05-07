package com.radlance.numberstesttask.random

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

interface WorkManagerWrapper {

    fun start()

    class Base(context: Context) : WorkManagerWrapper {
        private val workManager = WorkManager.getInstance(context)

        override fun start() {
            val request = PeriodicWorkRequestBuilder<PeriodicRandomWorker>(
                repeatInterval = 15,
                repeatIntervalTimeUnit = TimeUnit.MINUTES
            ).setInitialDelay(
                duration = 15,
                timeUnit = TimeUnit.MINUTES
            ).setConstraints(
                Constraints(
                    requiresBatteryNotLow = true,
                    requiredNetworkType = NetworkType.CONNECTED
                )
            ).build()

            workManager.enqueueUniquePeriodicWork(
                uniqueWorkName = WORK_NAME,
                existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
                request = request
            )
        }

        companion object {
            private const val WORK_NAME = "random number work"
        }
    }
}