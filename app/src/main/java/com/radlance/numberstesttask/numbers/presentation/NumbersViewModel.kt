package com.radlance.numberstesttask.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.radlance.numberstesttask.main.presentation.BaseViewModel
import com.radlance.numberstesttask.main.presentation.Init
import com.radlance.numberstesttask.main.presentation.NavigationCommunication
import com.radlance.numberstesttask.main.presentation.NavigationStrategy
import com.radlance.numberstesttask.main.presentation.Screen
import com.radlance.numberstesttask.main.presentation.UiFeature
import com.radlance.numberstesttask.numbers.domain.NumberDetailsUseCase

interface NumbersViewModel : ObserveNumbers, FetchNumbers, ClearError, Init, ShowDetails {

    class Base(
        coroutineDispatchers: CoroutineDispatchers,
        private val initial: UiFeature,
        private val numberFact: NumbersFactFeature,
        private val randomFact: UiFeature,
        private val showDetails: ShowDetails,
        private val communications: NumbersCommunications
    ) : BaseViewModel(coroutineDispatchers), NumbersViewModel {

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
            if (isFirstRun) initial.handle(this)
        }

        override fun fetchRandomNumberFact() {
            randomFact.handle(this)
        }

        override fun fetchNumberFact(number: String) {
           numberFact.number(number).handle(this)
        }

        override fun clearError() = communications.showState(UiState.ClearError)

        override fun showDetails(item: NumberUi) = showDetails.showDetails(item)
    }
}

interface ShowDetails {

    fun showDetails(item: NumberUi)

    class Base(
        private val communications: NavigationCommunication.Mutate,
        private val mapper: NumberUi.Mapper<String>,
        private val useCase: NumberDetailsUseCase
    ) : ShowDetails {
        override fun showDetails(item: NumberUi) {
            useCase.saveDetails(item.map(mapper))
            communications.map(
                NavigationStrategy.Add(Screen.Details)
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