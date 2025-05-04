package com.radlance.numberstesttask.details.presentation

import androidx.lifecycle.ViewModel
import com.radlance.numberstesttask.details.data.NumberFactDetails

class NumberDetailsViewModel(
    private val data: NumberFactDetails.Read
) : ViewModel(), NumberFactDetails.Read {

    override fun read(): String = data.read()
}