package com.radlance.numberstesttask.main.sl

import android.content.Context
import com.radlance.numberstesttask.details.data.NumberFactDetails
import com.radlance.numberstesttask.main.presentation.NavigationCommunication
import com.radlance.numberstesttask.numbers.data.cache.CacheModule
import com.radlance.numberstesttask.numbers.data.cache.NumbersDatabase
import com.radlance.numberstesttask.numbers.data.cloud.CloudModule
import com.radlance.numberstesttask.numbers.presentation.CoroutineDispatchers
import com.radlance.numberstesttask.numbers.presentation.ManageResources

interface Core : CloudModule, CacheModule, ManageResources, ProvideNavigation,
    ProvideNumberDetails {

    fun provideDispatchers(): CoroutineDispatchers

    class Base(
        context: Context,
        provideInstances: ProvideInstances,
    ) : Core {

        private val manageResources = ManageResources.Base(context)

        private val dispatchers by lazy { CoroutineDispatchers.Base() }

        private val cloudModule by lazy { provideInstances.provideCloudModule() }

        private val cacheModule by lazy { provideInstances.provideCacheModule() }

        private val navigationCommunication = NavigationCommunication.Base()

        private val numberFactDetails = NumberFactDetails.Base()

        override fun <T : Any> service(clazz: Class<T>): T = cloudModule.service(clazz)

        override fun provideDatabase(): NumbersDatabase = cacheModule.provideDatabase()

        override fun string(id: Int): String = manageResources.string(id)

        override fun provideNavigation(): NavigationCommunication.Mutable = navigationCommunication

        override fun provideNumberDetails(): NumberFactDetails.Mutable = numberFactDetails

        override fun provideDispatchers(): CoroutineDispatchers = dispatchers
    }
}

interface ProvideNavigation {
    fun provideNavigation(): NavigationCommunication.Mutable
}

interface ProvideNumberDetails {
    fun provideNumberDetails(): NumberFactDetails.Mutable
}