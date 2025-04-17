package com.radlance.numberstesttask.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radlance.numberstesttask.R
import com.radlance.numberstesttask.numbers.domain.NumbersInteractor

class NumbersViewModel(
    private val handleResult: HandleNumbersRequest,
    private val manageResources: ManageResources,
    private val communications: NumbersCommunications,
    private val interactor: NumbersInteractor,
    private val dispatcher: CoroutineDispatchers
) : ObserveNumbers, FetchNumbers, ViewModel() {

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) {
        communications.observeProgress(owner, observer)
    }

    override fun observeNumbersState(owner: LifecycleOwner, observer: Observer<UiState>) {
        communications.observeNumbersState(owner, observer)
    }

    override fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
        communications.observeNumbersList(owner, observer)
    }

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            handleResult.handle(viewModelScope, dispatcher) { interactor.init() }
        }
    }

    override fun fetchRandomNumberFact() {
        handleResult.handle(viewModelScope, dispatcher) { interactor.factAboutRandomNumber() }
    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty()) {
            communications.showState(UiState.Error(manageResources.string(R.string.empty_number_error_message)))
        } else {
            handleResult.handle(viewModelScope, dispatcher) { interactor.factAboutNumber(number) }
        }
    }
}


interface FetchNumbers {

    fun init(isFirstRun: Boolean)

    fun fetchRandomNumberFact()

    fun fetchNumberFact(number: String)
}