package com.radlance.numberstesttask.numbers.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {
    
    fun main(): CoroutineDispatcher
    
    fun io(): CoroutineDispatcher
    
    class Base : CoroutineDispatchers {
        
        override fun main(): CoroutineDispatcher = Dispatchers.Main

        override fun io(): CoroutineDispatcher = Dispatchers.IO
    }
}