package com.radlance.numberstesttask.main.sl

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.radlance.numberstesttask.BuildConfig

class NumbersApp : Application(), ProvideViewModel {

    private lateinit var viewModelsFactory: ViewModelsFactory
    override fun onCreate() {
        super.onCreate()

        viewModelsFactory = ViewModelsFactory(
            DependencyContainer.Base(Core.Base(context = this, isRelease = !BuildConfig.DEBUG))
        )
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T {
        return ViewModelProvider.create(owner, viewModelsFactory)[clazz]
    }
}