package com.radlance.numberstesttask.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.radlance.numberstesttask.main.presentation.NavigationCommunication
import com.radlance.numberstesttask.main.presentation.NavigationStrategy
import com.radlance.numberstesttask.numbers.presentation.ManageResources
import com.radlance.numberstesttask.numbers.presentation.NumberUi
import com.radlance.numberstesttask.numbers.presentation.NumbersCommunications
import com.radlance.numberstesttask.numbers.presentation.UiState

abstract class BaseTest {
    protected class TestNumbersCommunications : NumbersCommunications {
        var progressCalledList = mutableListOf<Int>()
        var stateCalledList = mutableListOf<UiState>()
        var timesShowList = 0
        val numbersList = mutableListOf<NumberUi>()

        override fun showProgress(show: Int) {
            progressCalledList.add(show)
        }

        override fun showState(state: UiState) {
            stateCalledList.add(state)
        }

        override fun showList(list: List<NumberUi>) {
            timesShowList++
            numbersList.addAll(list)
        }

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) = Unit

        override fun observeNumbersState(owner: LifecycleOwner, observer: Observer<UiState>) = Unit

        override fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) = Unit
    }

    protected class TestManageResources : ManageResources {
        private var string = ""

        fun makeExpectedAnswer(expected: String) {
            string = expected
        }

        override fun string(id: Int): String = string
    }

    protected class TestNavigationCommunication : NavigationCommunication.Mutable {

        lateinit var strategy: NavigationStrategy
        var count = 0

        override fun observe(owner: LifecycleOwner, observer: Observer<NavigationStrategy>) {}

        override fun map(source: NavigationStrategy) {
            strategy = source
            count++
        }
    }
}