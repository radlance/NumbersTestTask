package com.radlance.numberstesttask.numbers.presentation

import android.view.View
import com.radlance.numberstesttask.common.BaseTest
import com.radlance.numberstesttask.common.MainDispatcherRule
import com.radlance.numberstesttask.numbers.domain.NumberFact
import com.radlance.numberstesttask.numbers.domain.NumbersInteractor
import com.radlance.numberstesttask.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NumbersViewModelTest : BaseTest() {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var manageResources: TestManageResources
    private lateinit var communications: TestNumbersCommunications
    private lateinit var interactor: TestNumbersInteractor

    private lateinit var viewModel: NumbersViewModel

    @Before
    fun setup() {
        manageResources = TestManageResources()
        communications = TestNumbersCommunications()
        interactor = TestNumbersInteractor()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        viewModel = NumbersViewModel(
            manageResources = manageResources,
            communications = communications,
            interactor = interactor,
            handleResult = HandleNumbersRequest.Base(communications, mapper),
            dispatcher = TestDispatchers()
        )
    }
    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data successfully
     * then re-init and check the result
     */
    @Test
    fun `test init and re-init`() = runTest {
        interactor.changeExpectedResult(NumbersResult.Success())
        // 2. action
        viewModel.init(isFirstRun = true)
        // 3. check
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(View.VISIBLE, communications.progressCalledList[0])

        advanceUntilIdle()

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success, communications.stateCalledList[0])

        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.timesShowList)

        // 4. get some data
        interactor.changeExpectedResult(NumbersResult.Failure("no internet connection"))
        viewModel.fetchRandomNumberFact()

        assertEquals(3, communications.progressCalledList.size)
        assertEquals(View.VISIBLE, communications.progressCalledList[2])

        advanceUntilIdle()

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.ShowError("no internet connection"), communications.stateCalledList[1])
        assertEquals(0, communications.timesShowList)

        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(0, communications.timesShowList)
    }

    /**
     * Try to get information about empty number
     */
    @Test
    fun `fact about empty number`() {
        manageResources.makeExpectedAnswer("entered number is empty")
        viewModel.fetchNumberFact("")

        assertEquals(0, interactor.fetchAboutNumberCalledList.size)
        assertEquals(0, communications.progressCalledList.size)

        assertEquals(UiState.ShowError("entered number is empty"), communications.stateCalledList[0])
        assertEquals(1, communications.stateCalledList.size)

        assertEquals(0, communications.timesShowList)
        assertEquals(0, interactor.fetchAboutRandomNumberCalledList.size)
    }

    /**
     * Try to get information about some number
     */
    @Test
    fun `fact about some number`() = runTest {
        interactor.changeExpectedResult(
            NumbersResult.Success(
                listOf(
                    NumberFact(
                        "45",
                        "fact about 45"
                    )
                )
            )
        )

        viewModel.fetchNumberFact("45")


        assertEquals(1, communications.progressCalledList.size)
        assertEquals(View.VISIBLE, communications.progressCalledList[0])

        advanceUntilIdle()

        assertEquals(1, interactor.fetchAboutNumberCalledList.size)
        assertEquals(
            NumbersResult.Success(listOf(NumberFact("45", "fact about 45"))),
            interactor.fetchAboutNumberCalledList[0]
        )
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("45", "fact about 45"), communications.numbersList[0])
    }

    @Test
    fun `test clear error`() = runTest {
        viewModel.clearError()
        assertEquals(1, communications.stateCalledList.size)
        assertTrue(communications.stateCalledList[0] is UiState.ClearError)
    }

    private class TestNumbersInteractor : NumbersInteractor {
        private var result: NumbersResult = NumbersResult.Success()

        val initCalledList = mutableListOf<NumbersResult>()
        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun factAboutNumber(number: String): NumbersResult {
            fetchAboutNumberCalledList.add(result)
            return result
        }

        override suspend fun factAboutRandomNumber(): NumbersResult {
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }
    }

    private class TestDispatchers : CoroutineDispatchers {

        override fun main(): CoroutineDispatcher = Dispatchers.Main

        override fun io(): CoroutineDispatcher = Dispatchers.Main
    }
}