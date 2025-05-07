package com.radlance.numberstesttask.numbers.presentation

import com.radlance.numberstesttask.R
import com.radlance.numberstesttask.main.presentation.Handle
import com.radlance.numberstesttask.main.presentation.UiFeature
import com.radlance.numberstesttask.numbers.domain.NumbersFactUseCase
import com.radlance.numberstesttask.numbers.domain.NumbersResult
import kotlinx.coroutines.Job

interface NumbersFactFeature : UiFeature, suspend () -> NumbersResult {

    fun number(number: String): UiFeature

    class Base(
        mapper: NumbersResult.Mapper<Unit>,
        communications: NumbersCommunications,
        private val manageResources: ManageResources,
        private val useCase: NumbersFactUseCase
    ) : NumbersFeature(communications, mapper), NumbersFactFeature {

        private var number = "0"

        override fun number(number: String): UiFeature {
            this.number = number
            return this
        }

        override fun handle(handle: Handle): Job {
            return if (number.isEmpty()) {
                handle.handle(
                    block = {
                        NumbersResult.Failure(
                            manageResources.string(R.string.empty_number_error_message)
                        )
                    }
                ) { showUi(result = it) }
            } else {
                super.handle(handle)
            }
        }

        override suspend fun invoke(): NumbersResult = useCase.factAboutNumber(number)
    }
}
