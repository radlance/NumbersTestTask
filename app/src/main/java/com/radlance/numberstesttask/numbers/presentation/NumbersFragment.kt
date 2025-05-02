package com.radlance.numberstesttask.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.radlance.numberstesttask.R
import com.radlance.numberstesttask.main.presentation.ShowFragment

class NumbersFragment : Fragment() {
    private var showFragment: ShowFragment = ShowFragment.Empty()

    private lateinit var viewModel: NumbersViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showFragment = context as ShowFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val factButton = view.findViewById<Button>(R.id.factButton)
        val randomButton = view.findViewById<Button>(R.id.randomFactButton)
        val inputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayout)
        val inputEditText = view.findViewById<TextInputEditText>(R.id.inputEditText)
        val recyclerView = view.findViewById<RecyclerView>(R.id.historyRecyclerView)

        val adapter = NumbersAdapter(
            object : ClickListener {
                override fun click(item: NumberUi) {
                    // TODO move to next screen showFragment.show(DetailsFragment.newInstance("some information about number hardcoded"))
                }
            }
        )
        recyclerView.adapter = adapter

        inputEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                viewModel.clearError()
            }
        })
        factButton.setOnClickListener {
            viewModel.fetchNumberFact(inputEditText.text.toString())
        }

        randomButton.setOnClickListener {
            viewModel.fetchRandomNumberFact()
        }

        viewModel.observeNumbersState(this) {
            it.apply(inputLayout, inputEditText)
        }

        viewModel.observeNumbersList(this) {
            adapter.map(it)
        }

        viewModel.observeProgress(this) {
            progressBar.visibility = it
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onDetach() {
        super.onDetach()
        showFragment = ShowFragment.Empty()
    }
}

abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    override fun afterTextChanged(s: Editable?) = Unit
}