package com.radlance.numberstesttask.main.sl

import androidx.lifecycle.ViewModel
import com.radlance.numberstesttask.details.presentation.NumberDetailsViewModel
import com.radlance.numberstesttask.details.sl.NumberDetailsModule
import com.radlance.numberstesttask.main.presentation.MainViewModel
import com.radlance.numberstesttask.numbers.domain.NumbersRepository
import com.radlance.numberstesttask.numbers.presentation.NumbersViewModel
import com.radlance.numberstesttask.numbers.sl.NumbersModule
import com.radlance.numberstesttask.numbers.sl.ProvideNumbersRepository

interface DependencyContainer {

    fun <T: ViewModel> module(clazz: Class<T>): Module<*>

    class Error : DependencyContainer {
        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> {
            throw IllegalStateException("no module found for $clazz")
        }
    }

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error()
    ) : DependencyContainer, ProvideNumbersRepository {

        private val repository by lazy {
            ProvideNumbersRepository.Base(core).provideNumbersRepository()
        }

        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> = when (clazz) {
            MainViewModel::class.java -> MainModule(core)
            NumbersViewModel.Base::class.java -> NumbersModule(core, this)
            NumberDetailsViewModel::class.java -> NumberDetailsModule(core)
            else -> dependencyContainer.module(clazz)
        }

        override fun provideNumbersRepository(): NumbersRepository = repository
    }
}