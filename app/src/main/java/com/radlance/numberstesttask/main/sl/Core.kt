package com.radlance.numberstesttask.main.sl

import android.content.Context
import com.radlance.numberstesttask.numbers.data.cache.CacheModule
import com.radlance.numberstesttask.numbers.data.cache.NumbersDatabase
import com.radlance.numberstesttask.numbers.data.cloud.CloudModule
import com.radlance.numberstesttask.numbers.presentation.CoroutineDispatchers
import com.radlance.numberstesttask.numbers.presentation.ManageResources

interface Core : CloudModule, CacheModule, ManageResources {

    fun provideDispatchers(): CoroutineDispatchers

    class Base(
        context: Context,
        private val isRelease: Boolean
    ) : Core {

        private val manageResources = ManageResources.Base(context)

        private val dispatchers by lazy { CoroutineDispatchers.Base() }

        private val cloudModule by lazy {
            if (isRelease) {
                CloudModule.Base()
            } else {
                CloudModule.Mock()
            }
        }

        private val cacheModule by lazy {
            if (isRelease) {
                CacheModule.Base(context)
            } else {
                CacheModule.Mock(context)
            }
        }

        override fun <T : Any> service(clazz: Class<T>): T = cloudModule.service(clazz)

        override fun provideDatabase(): NumbersDatabase = cacheModule.provideDatabase()

        override fun string(id: Int): String = manageResources.string(id)

        override fun provideDispatchers(): CoroutineDispatchers = dispatchers
    }
}