package com.radlance.numberstesttask.main

import android.app.Application
import com.radlance.numberstesttask.BuildConfig
import com.radlance.numberstesttask.numbers.data.cloud.CloudModule

class NumbersApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val cloudModule = if (BuildConfig.DEBUG) {
            CloudModule.Debug()
        } else {
            CloudModule.Release()
        }
    }
}