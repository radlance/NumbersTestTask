package com.radlance.numberstesttask.numbers.presentation

import android.view.View
import com.radlance.numberstesttask.main.presentation.Handle
import com.radlance.numberstesttask.main.presentation.UiFeature
import com.radlance.numberstesttask.numbers.domain.NumbersResult
import kotlinx.coroutines.Job

abstract class NumbersFeature(
    private val communications: NumbersCommunications,
    private val mapper: NumbersResult.Mapper<Unit>
) : UiFeature, suspend () -> NumbersResult {
    override fun handle(handle: Handle): Job {
        communications.showProgress(View.VISIBLE)
        return handle.handle(block = this) { result ->
            communications.showProgress(View.GONE)
            showUi(result)
        }
    }

    protected fun showUi(result: NumbersResult) = result.map(mapper)
}