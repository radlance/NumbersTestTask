package com.radlance.numberstesttask.main.sl

import com.radlance.numberstesttask.main.presentation.MainViewModel

class MainModule(private val core: Core) : Module<MainViewModel> {
    override fun viewModel(): MainViewModel {
        return MainViewModel(
            navigationCommunication = core.provideNavigation(),
            workManagerWrapper = core.provideWorkManagerWrapper()
        )
    }
}