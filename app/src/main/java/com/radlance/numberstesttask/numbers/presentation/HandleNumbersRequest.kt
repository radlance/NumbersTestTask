package com.radlance.numberstesttask.numbers.presentation

import com.radlance.numberstesttask.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandleNumbersRequest {
    fun handle(
        coroutineScope: CoroutineScope,
        action: suspend () -> NumbersResult
    )

    class Base(
        private val communications: NumbersCommunications,
        private val mapper: NumbersResult.Mapper<Unit>
    ) : HandleNumbersRequest {
        override fun handle(coroutineScope: CoroutineScope, action: suspend () -> NumbersResult) {
            communications.showProgress(true)
            coroutineScope.launch {
                val result = action.invoke()
                communications.showProgress(false)
                result.map(mapper)
            }
        }
    }
}