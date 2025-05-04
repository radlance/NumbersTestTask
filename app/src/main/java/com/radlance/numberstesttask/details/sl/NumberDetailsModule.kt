package com.radlance.numberstesttask.details.sl

import com.radlance.numberstesttask.details.presentation.NumberDetailsViewModel
import com.radlance.numberstesttask.main.sl.Module
import com.radlance.numberstesttask.main.sl.ProvideNumberDetails

class NumberDetailsModule(
    private val provideNumberDetails: ProvideNumberDetails
) : Module<NumberDetailsViewModel> {
    override fun viewModel(): NumberDetailsViewModel {
        return NumberDetailsViewModel(provideNumberDetails.provideNumberDetails())
    }
}