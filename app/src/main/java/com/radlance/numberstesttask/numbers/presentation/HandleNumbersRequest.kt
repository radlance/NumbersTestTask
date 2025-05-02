package com.radlance.numberstesttask.numbers.presentation

import android.view.View
import com.radlance.numberstesttask.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface HandleNumbersRequest {
    fun handle(
        coroutineScope: CoroutineScope,
        dispatchers: CoroutineDispatchers,
        action: suspend () -> NumbersResult
    )

    class Base(
        private val communications: NumbersCommunications,
        private val mapper: NumbersResult.Mapper<Unit>
    ) : HandleNumbersRequest {
        override fun handle(
            coroutineScope: CoroutineScope,
            dispatchers: CoroutineDispatchers,
            action: suspend () -> NumbersResult
        ) {
            communications.showProgress(View.VISIBLE)
            coroutineScope.launch(dispatchers.io()) {
                val result = action.invoke()

                withContext(dispatchers.main()) {
                    communications.showProgress(View.GONE)
                    result.map(mapper)
                }
            }
        }
    }
}