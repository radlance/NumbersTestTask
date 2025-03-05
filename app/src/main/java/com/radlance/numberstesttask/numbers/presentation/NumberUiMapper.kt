package com.radlance.numberstesttask.numbers.presentation

import com.radlance.numberstesttask.numbers.domain.NumberFact

class NumberUiMapper : NumberFact.Mapper<NumberUi> {
    override fun map(id: String, fact: String): NumberUi = NumberUi(id, fact)
}