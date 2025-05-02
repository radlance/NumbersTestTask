package com.radlance.numberstesttask.main.sl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelsFactory(
    private val dependencyContainer: DependencyContainer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return dependencyContainer.module(modelClass).viewModel() as T
    }
}