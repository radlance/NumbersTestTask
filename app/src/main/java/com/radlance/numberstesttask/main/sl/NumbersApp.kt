package com.radlance.numberstesttask.main.sl

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.radlance.numberstesttask.BuildConfig
import com.radlance.numberstesttask.numbers.domain.RandomNumberRepository
import com.radlance.numberstesttask.random.ProvidePeriodicRepository

class NumbersApp : Application(), ProvideViewModel, ProvidePeriodicRepository {

    private lateinit var viewModelsFactory: ViewModelsFactory
    private lateinit var dependencyContainer: DependencyContainer.Base

    override fun onCreate() {
        super.onCreate()

        val provideInstances = if (!BuildConfig.DEBUG) {
            ProvideInstances.Mock(this)
        } else {
            ProvideInstances.Release(this)
        }

        dependencyContainer = DependencyContainer.Base(
            Core.Base(context = this, provideInstances = provideInstances)
        )

        viewModelsFactory = ViewModelsFactory(dependencyContainer)
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T {
        return ViewModelProvider.create(owner, viewModelsFactory)[clazz]
    }

    override fun providePeriodicRepository(): RandomNumberRepository {
        return dependencyContainer.provideNumbersRepository()
    }
}