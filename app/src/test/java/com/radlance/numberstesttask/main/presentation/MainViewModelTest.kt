package com.radlance.numberstesttask.main.presentation

import com.radlance.numberstesttask.common.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MainViewModelTest : BaseTest() {

    @Test
    fun `test navigation at start`() {
        val navigation = TestNavigationCommunication()
        val mainViewModel = MainViewModel(navigationCommunication = navigation)
        mainViewModel.init(isFirstRun = true)

        assertEquals(1, navigation.count)
        assertTrue(navigation.strategy is NavigationStrategy.Replace)

        mainViewModel.init(isFirstRun = false)
        assertEquals(1, navigation.count)
    }
}