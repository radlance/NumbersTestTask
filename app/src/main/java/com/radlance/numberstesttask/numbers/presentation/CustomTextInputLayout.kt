package com.radlance.numberstesttask.numbers.presentation

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

interface CustomTextInputLayout {

    fun changeErrorEnabled(enabled: Boolean)

    fun showError(errorMessage: String)
}

class BaseCustomTextInputLayout : TextInputLayout, CustomTextInputLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun changeErrorEnabled(enabled: Boolean) {
        isErrorEnabled = enabled
    }

    override fun showError(errorMessage: String) {
        error = errorMessage
    }
}