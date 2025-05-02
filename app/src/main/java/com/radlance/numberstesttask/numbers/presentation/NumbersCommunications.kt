package com.radlance.numberstesttask.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface NumbersCommunications : ObserveNumbers {
    fun showProgress(show: Int)

    fun showState(state: UiState)

    fun showList(list: List<NumberUi>)

    class Base(
        private val progress: ProgressCommunication,
        private val numbersState: NumbersStateCommunications,
        private val numbersList: NumbersListCommunications
    ) : NumbersCommunications {
        override fun showProgress(show: Int) = progress.map(show)

        override fun showState(state: UiState) = numbersState.map(state)

        override fun showList(list: List<NumberUi>) = numbersList.map(list)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) {
            progress.observe(owner, observer)
        }

        override fun observeNumbersState(owner: LifecycleOwner, observer: Observer<UiState>) {
            numbersState.observe(owner, observer)
        }

        override fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
            numbersList.observe(owner, observer)
        }
    }
}

interface ObserveNumbers {
    fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>)

    fun observeNumbersState(owner: LifecycleOwner, observer: Observer<UiState>)

    fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>)
}

interface ProgressCommunication : Communication.Mutable<Int> {
    class Base : Communication.Post<Int>(), ProgressCommunication
}

interface NumbersStateCommunications : Communication.Mutable<UiState> {
    class Base : Communication.Post<UiState>(), NumbersStateCommunications
}

interface NumbersListCommunications : Communication.Mutable<List<NumberUi>> {
    class Base : Communication.Post<List<NumberUi>>(), NumbersListCommunications
}