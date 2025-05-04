package com.radlance.numberstesttask.main.sl

import com.radlance.numberstesttask.main.presentation.MainViewModel

class MainModule(private val provideNavigation: ProvideNavigation) : Module<MainViewModel> {
    override fun viewModel(): MainViewModel {
        return MainViewModel(navigationCommunication = provideNavigation.provideNavigation())
    }
}