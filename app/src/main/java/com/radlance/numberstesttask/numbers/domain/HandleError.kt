package com.radlance.numberstesttask.numbers.domain

import com.radlance.numberstesttask.R
import com.radlance.numberstesttask.numbers.presentation.ManageResources

interface HandleError {
    fun handle(e: Exception): String

    class Base(private val manageResources: ManageResources) : HandleError {
        override fun handle(e: Exception): String {
            val errorStringId = if (e is NoInternetConnectionException) {
                R.string.no_connection_message
            } else {
                R.string.service_is_unavailable
            }
            return manageResources.string(errorStringId)
        }
    }
}
