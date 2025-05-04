package com.radlance.numberstesttask.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.radlance.numberstesttask.databinding.FragmentNumberDetailsBinding
import com.radlance.numberstesttask.main.presentation.BaseFragment

class NumberDetailsFragment : BaseFragment<FragmentNumberDetailsBinding, NumberDetailsViewModel>() {

    override fun viewModelClass() = NumberDetailsViewModel::class.java

    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentNumberDetailsBinding {
        return FragmentNumberDetailsBinding.inflate(inflater, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailsTextView.text = viewModel.read()
    }
}