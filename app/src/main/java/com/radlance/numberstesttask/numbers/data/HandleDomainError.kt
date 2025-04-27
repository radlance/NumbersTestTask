package com.radlance.numberstesttask.numbers.data

import com.radlance.numberstesttask.numbers.domain.HandleError
import com.radlance.numberstesttask.numbers.domain.NoInternetConnectionException
import com.radlance.numberstesttask.numbers.domain.ServiceUnavailableException
import java.net.UnknownHostException

class HandleDomainError : HandleError<Exception> {
    override fun handle(e: Exception): Exception {
        return when (e) {
            is UnknownHostException -> NoInternetConnectionException()
            else -> ServiceUnavailableException()
        }
    }
}