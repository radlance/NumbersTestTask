package com.radlance.numberstesttask.main.presentation

import com.radlance.numberstesttask.common.BaseTest
import com.radlance.numberstesttask.random.WorkManagerWrapper
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest : BaseTest() {

    @Test
    fun `test navigation at start`() {
        val navigation = TestNavigationCommunication()
        val workManagerWrapper = TestWorkManagerWrapper()

        val mainViewModel = MainViewModel(
            navigationCommunication = navigation,
            workManagerWrapper = workManagerWrapper
        )
        mainViewModel.init(isFirstRun = true)

        assertEquals(1, navigation.count)
        assertEquals(1, workManagerWrapper.startCalledCount)
        assertEquals(NavigationStrategy.Replace(Screen.Numbers), navigation.strategy)

        mainViewModel.init(isFirstRun = false)
        assertEquals(1, navigation.count)
        assertEquals(1, workManagerWrapper.startCalledCount)
    }

    private class TestWorkManagerWrapper : WorkManagerWrapper {
        var startCalledCount = 0

        override fun start() {
            startCalledCount++
        }
    }
}