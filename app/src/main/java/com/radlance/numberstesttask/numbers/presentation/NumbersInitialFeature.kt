package com.radlance.numberstesttask.numbers.presentation

import com.radlance.numberstesttask.numbers.domain.NumbersInitialUseCase
import com.radlance.numberstesttask.numbers.domain.NumbersResult

class NumbersInitialFeature(
    communications: NumbersCommunications,
    mapper: NumbersResult.Mapper<Unit>,
    private val useCase: NumbersInitialUseCase
) : NumbersFeature(communications, mapper) {

    override suspend fun invoke(): NumbersResult = useCase.init()
}