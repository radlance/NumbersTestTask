package com.radlance.numberstesttask.numbers.presentation

interface UiState {

    fun apply(inputLayout: CustomTextInputLayout, textInputEditText: CustomTextInputEditText)

    object Success : UiState {
        override fun apply(
            inputLayout: CustomTextInputLayout,
            textInputEditText: CustomTextInputEditText
        ) = textInputEditText.showText("")
    }

    abstract class AbstractError(
        private val message: String,
        private var errorEnabled: Boolean
    ): UiState {
        override fun apply(
            inputLayout: CustomTextInputLayout,
            textInputEditText: CustomTextInputEditText
        ) = with(inputLayout) {
            changeErrorEnabled(errorEnabled)
            showError(message)
        }
    }
    data class ShowError(private val message: String) : AbstractError(message = message, errorEnabled = true)

    object ClearError : AbstractError(message = "", errorEnabled = false)
}