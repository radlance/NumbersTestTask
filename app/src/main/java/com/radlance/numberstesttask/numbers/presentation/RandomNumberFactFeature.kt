package com.radlance.numberstesttask.numbers.presentation

import com.radlance.numberstesttask.numbers.domain.NumbersResult
import com.radlance.numberstesttask.numbers.domain.RandomNumbersUseCase

class RandomNumberFactFeature(
    communications: NumbersCommunications,
    mapper: NumbersResult.Mapper<Unit>,
    private val useCase: RandomNumbersUseCase
) : NumbersFeature(communications, mapper) {
    override suspend fun invoke(): NumbersResult = useCase.factAboutRandomNumber()
}