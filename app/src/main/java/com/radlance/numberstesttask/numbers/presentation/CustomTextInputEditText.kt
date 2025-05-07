package com.radlance.numberstesttask.numbers.presentation

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

interface CustomTextInputEditText {

    fun showText(text: String)
}

class BaseCustomTextInputEditText : TextInputEditText, CustomTextInputEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun showText(text: String) {
        setText(text)
    }
}
