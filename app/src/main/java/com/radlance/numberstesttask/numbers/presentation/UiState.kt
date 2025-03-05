package com.radlance.numberstesttask.numbers.presentation

interface UiState {
    object Success : UiState
    data class Error(private val message: String) : UiState
}