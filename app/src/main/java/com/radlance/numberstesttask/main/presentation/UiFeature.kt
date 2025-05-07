package com.radlance.numberstesttask.main.presentation

import kotlinx.coroutines.Job

interface UiFeature {

    fun handle(handle: Handle): Job
}