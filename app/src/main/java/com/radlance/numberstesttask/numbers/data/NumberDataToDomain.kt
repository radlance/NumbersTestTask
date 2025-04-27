package com.radlance.numberstesttask.numbers.data

import com.radlance.numberstesttask.numbers.domain.NumberFact

class NumberDataToDomain : NumberData.Mapper<NumberFact> {
    override fun map(id: String, fact: String): NumberFact {
        return NumberFact(id, fact)
    }
}