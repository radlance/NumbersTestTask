package com.radlance.numberstesttask.numbers.presentation

import com.radlance.numberstesttask.numbers.domain.NumberFact
import org.junit.Assert.assertEquals
import org.junit.Test

class NumbersResultMapperTest : BaseTest() {
    @Test
    fun test_success_empty_list() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.mapSuccess(emptyList())
        assertEquals(0, communications.timesShowList)
        assertEquals(UiState.Success, communications.stateCalledList[0])
    }

    @Test
    fun test_success_with_list() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())
        mapper.mapSuccess(listOf(NumberFact(id = "42", fact = "fact 42")))

        assertEquals(
            NumberUi(id = "42", fact = "fact 42"),
            communications.numbersList[0]
        )

        assertEquals(1, communications.timesShowList)
        assertEquals(UiState.Success, communications.stateCalledList[0])
    }

    @Test
    fun test_error() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.mapError(message = "no connection")
        assertEquals(UiState.Error("no connection"), communications.stateCalledList[0])
    }
}