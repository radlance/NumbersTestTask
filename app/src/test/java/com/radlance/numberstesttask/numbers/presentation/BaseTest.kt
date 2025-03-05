package com.radlance.numberstesttask.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

abstract class BaseTest {
    protected class TestNumbersCommunications : NumbersCommunications {
        var progressCalledList = mutableListOf<Boolean>()
        var stateCalledList = mutableListOf<UiState>()
        var timesShowList = 0
        val numbersList = mutableListOf<NumberUi>()

        override fun showProgress(show: Boolean) {
            progressCalledList.add(show)
        }

        override fun showState(state: UiState) {
            stateCalledList.add(state)
        }

        override fun showList(list: List<NumberUi>) {
            timesShowList++
            numbersList.addAll(list)
        }

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) = Unit

        override fun observeNumbersState(owner: LifecycleOwner, observer: Observer<UiState>) = Unit

        override fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) = Unit
    }
}