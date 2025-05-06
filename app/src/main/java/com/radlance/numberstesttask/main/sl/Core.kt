package com.radlance.numberstesttask.main.sl

import android.content.Context
import com.radlance.numberstesttask.details.data.NumberFactDetails
import com.radlance.numberstesttask.main.presentation.NavigationCommunication
import com.radlance.numberstesttask.numbers.data.cache.CacheModule
import com.radlance.numberstesttask.numbers.data.cache.NumbersDatabase
import com.radlance.numberstesttask.numbers.data.cloud.CloudModule
import com.radlance.numberstesttask.numbers.data.cloud.RandomApiHeader
import com.radlance.numberstesttask.numbers.presentation.CoroutineDispatchers
import com.radlance.numberstesttask.numbers.presentation.ManageResources
import com.radlance.numberstesttask.random.WorkManagerWrapper

interface Core : CloudModule, CacheModule, ManageResources, ProvideNavigation,
    ProvideNumberDetails, ProvideRandomApiHeader, ProvideWorkManagerWrapper {

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

        private val randomNumberApiHeader = provideInstances.provideRandomApiHeader()

        private val workManagerWrapper = WorkManagerWrapper.Base(context)

        override fun <T : Any> service(clazz: Class<T>): T = cloudModule.service(clazz)

        override fun provideDatabase(): NumbersDatabase = cacheModule.provideDatabase()

        override fun string(id: Int): String = manageResources.string(id)

        override fun provideNavigation(): NavigationCommunication.Mutable = navigationCommunication

        override fun provideNumberDetails(): NumberFactDetails.Mutable = numberFactDetails

        override fun provideRandomApiHeader(): RandomApiHeader.Combo = randomNumberApiHeader

        override fun provideWorkManagerWrapper(): WorkManagerWrapper = workManagerWrapper

        override fun provideDispatchers(): CoroutineDispatchers = dispatchers
    }
}

interface ProvideWorkManagerWrapper {
    fun provideWorkManagerWrapper(): WorkManagerWrapper
}

interface ProvideNavigation {
    fun provideNavigation(): NavigationCommunication.Mutable
}

interface ProvideNumberDetails {
    fun provideNumberDetails(): NumberFactDetails.Mutable
}