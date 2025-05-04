package com.radlance.numberstesttask.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radlance.numberstesttask.R
import com.radlance.numberstesttask.details.presentation.NumberDetailsFragment
import com.radlance.numberstesttask.main.presentation.Init
import com.radlance.numberstesttask.main.presentation.NavigationCommunication
import com.radlance.numberstesttask.main.presentation.NavigationStrategy
import com.radlance.numberstesttask.numbers.domain.NumbersInteractor

interface NumbersViewModel : ObserveNumbers, FetchNumbers, ClearError, Init {

    fun showDetails(item: NumberUi)

    class Base(
        private val handleResult: HandleNumbersRequest,
        private val manageResources: ManageResources,
        private val communications: NumbersCommunications,
        private val interactor: NumbersInteractor,
        private val navigationCommunication: NavigationCommunication.Mutate,
        private val detailMapper: NumberUi.Mapper<String>
    ) : ViewModel(), NumbersViewModel {

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) {
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
                handleResult.handle(viewModelScope) { interactor.init() }
            }
        }

        override fun fetchRandomNumberFact() {
            handleResult.handle(viewModelScope) { interactor.factAboutRandomNumber() }
        }

        override fun fetchNumberFact(number: String) {
            if (number.isEmpty()) {
                communications.showState(UiState.ShowError(manageResources.string(R.string.empty_number_error_message)))
            } else {
                handleResult.handle(viewModelScope) { interactor.factAboutNumber(number) }
            }
        }

        override fun clearError() = communications.showState(UiState.ClearError)

        override fun showDetails(item: NumberUi) {
            interactor.saveDetails(item.map(detailMapper))
            navigationCommunication.map(
                NavigationStrategy.Add(NumberDetailsFragment())
            )
        }
    }
}

interface FetchNumbers {

    fun fetchRandomNumberFact()

    fun fetchNumberFact(number: String)
}

interface ClearError {
    fun clearError()
}