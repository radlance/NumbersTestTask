package com.radlance.numberstesttask.numbers.presentation

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class UiStateTest {

    private lateinit var inputLayout: TestCustomTextInputLayout
    private lateinit var textInputEditText: TestCustomTextInputEditText

    @Before
    fun setup() {
        inputLayout = TestCustomTextInputLayout()
        textInputEditText = TestCustomTextInputEditText()
    }

    @Test
    fun test_success() {
        val uiState = UiState.Success
        uiState.apply(inputLayout, textInputEditText)

        assertEquals(1, textInputEditText.showTextCalledList.size)
        assertEquals("", textInputEditText.showTextCalledList.first())
        assertEquals(0, inputLayout.showErrorCalledList.size)
        assertEquals(0, inputLayout.changeErrorCalledList.size)
    }

    @Test
    fun test_show_error() {
        val uiState = UiState.ShowError(message = "test")
        uiState.apply(inputLayout, textInputEditText)

        assertEquals(1, inputLayout.showErrorCalledList.size)
        assertEquals("test", inputLayout.showErrorCalledList.first())
        assertEquals(1, inputLayout.changeErrorCalledList.size)
        assertTrue(inputLayout.changeErrorCalledList.first())
        assertEquals(0, textInputEditText.showTextCalledList.size)
    }

    @Test
    fun test_clear_error() {
        val uiState = UiState.ClearError
        uiState.apply(inputLayout, textInputEditText)

        assertEquals(1, inputLayout.showErrorCalledList.size)
        assertEquals("", inputLayout.showErrorCalledList.first())
        assertEquals(1, inputLayout.changeErrorCalledList.size)
        assertFalse(inputLayout.changeErrorCalledList.first())
        assertEquals(0, textInputEditText.showTextCalledList.size)
    }


    private class TestCustomTextInputLayout : CustomTextInputLayout {

        val changeErrorCalledList = mutableListOf<Boolean>()
        val showErrorCalledList = mutableListOf<String>()

        override fun changeErrorEnabled(enabled: Boolean) {
            changeErrorCalledList.add(enabled)
        }

        override fun showError(errorMessage: String) {
            showErrorCalledList.add(errorMessage)
        }
    }

    private class TestCustomTextInputEditText : CustomTextInputEditText {

        val showTextCalledList = mutableListOf<String>()

        override fun showText(text: String) {
            showTextCalledList.add(text)
        }
    }
}