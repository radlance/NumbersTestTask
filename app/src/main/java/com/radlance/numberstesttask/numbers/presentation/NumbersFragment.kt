package com.radlance.numberstesttask.numbers.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import com.radlance.numberstesttask.databinding.FragmentNumbersBinding
import com.radlance.numberstesttask.main.presentation.BaseFragment

class NumbersFragment : BaseFragment<FragmentNumbersBinding, NumbersViewModel.Base>() {

    private lateinit var inputEditText: TextInputEditText

    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            super.afterTextChanged(s)
            viewModel.clearError()
        }
    }

    override fun viewModelClass() = NumbersViewModel.Base::class.java

    override fun inflate(inflater: LayoutInflater, parent: ViewGroup?): FragmentNumbersBinding {
        return FragmentNumbersBinding.inflate(inflater, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputEditText = binding.inputEditText

        val adapter = NumbersAdapter(
            object : ClickListener {
                override fun click(item: NumberUi) = viewModel.showDetails(item)
            }
        )
        binding.historyRecyclerView.adapter = adapter

        binding.factButton.setOnClickListener {
            viewModel.fetchNumberFact(inputEditText.text.toString())
        }

        binding.randomFactButton.setOnClickListener {
            viewModel.fetchRandomNumberFact()
        }

        viewModel.observeNumbersState(this) {
            it.apply(binding.textInputLayout, inputEditText)
        }

        viewModel.observeNumbersList(this) {
            adapter.map(it)
        }

        viewModel.observeProgress(this) {
            binding.progressBar.visibility = it
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        inputEditText.addTextChangedListener(watcher)
    }

    override fun onPause() {
        super.onPause()
        inputEditText.removeTextChangedListener(watcher)
    }
}

abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    override fun afterTextChanged(s: Editable?) = Unit
}