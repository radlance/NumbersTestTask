package com.radlance.numberstesttask.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radlance.numberstesttask.numbers.presentation.CoroutineDispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    private val dispatchers: CoroutineDispatchers
) : ViewModel(), Handle {

    override fun <T : Any> handle(
        block: suspend () -> T,
        ui: (T) -> Unit
    ) = viewModelScope.launch(dispatchers.io()) {
        val result = block.invoke()
        withContext(dispatchers.main()) {
            ui.invoke(result)
        }
    }
}

interface Handle {
    fun <T : Any> handle(block: suspend () -> T, ui: (T) -> Unit): Job
}