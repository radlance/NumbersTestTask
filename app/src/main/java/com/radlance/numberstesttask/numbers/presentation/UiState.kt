package com.radlance.numberstesttask.numbers.presentation

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface UiState {

    fun apply(inputLayout: TextInputLayout, textInputEditText: TextInputEditText)

    object Success : UiState {
        override fun apply(
            inputLayout: TextInputLayout,
            textInputEditText: TextInputEditText
        ) = textInputEditText.setText("")
    }

    abstract class AbstractError(
        private val message: String,
        private var errorEnabled: Boolean
    ): UiState {
        override fun apply(
            inputLayout: TextInputLayout,
            textInputEditText: TextInputEditText
        ) = with(inputLayout) {
            isErrorEnabled = errorEnabled
            error = message
        }
    }
    data class ShowError(private val message: String) : AbstractError(message = message, errorEnabled = true)

    object ClearError : AbstractError(message = "", errorEnabled = false)
}