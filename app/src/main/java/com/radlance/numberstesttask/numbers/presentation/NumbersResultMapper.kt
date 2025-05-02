package com.radlance.numberstesttask.numbers.presentation

import com.radlance.numberstesttask.numbers.domain.NumberFact
import com.radlance.numberstesttask.numbers.domain.NumbersResult

class NumbersResultMapper(
    private val communications: NumbersCommunications,
    private val numberUiMapper: NumberFact.Mapper<NumberUi>
) : NumbersResult.Mapper<Unit> {
    override fun mapSuccess(list: List<NumberFact>) {
        if (list.isNotEmpty()) {
            communications.showList(list.map { it.map(numberUiMapper) })
        }

        communications.showState(UiState.Success)
    }

    override fun mapError(message: String) {
        communications.showState(UiState.ShowError(message))
    }
}