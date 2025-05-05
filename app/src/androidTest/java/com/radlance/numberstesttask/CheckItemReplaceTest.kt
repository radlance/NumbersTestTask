package com.radlance.numberstesttask

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.radlance.numberstesttask.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckItemReplaceTest : BaseTest() {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_history() {
        val numbersPage = NumbersPage()
        with(numbersPage) {
            input.view().typeText("1")
            factButton.view().click()

            with(recycler) {
                viewInRecycler(position = 0, itemId = titleItem).checkText("1")
                viewInRecycler(position = 0, itemId = subTitleItem).checkText("fact about 1")
            }

            input.view().typeText("2")
            factButton.view().click()

            with(recycler) {
                viewInRecycler(position = 0, itemId = titleItem).checkText("2")
                viewInRecycler(position = 0, itemId = subTitleItem).checkText("fact about 2")
                viewInRecycler(position = 1, itemId = titleItem).checkText("1")
                viewInRecycler(position = 1, itemId = subTitleItem).checkText("fact about 1")
            }

            input.view().typeText("1")
            factButton.view().click()

            with(recycler) {
                viewInRecycler(position = 1, itemId = titleItem).checkText("2")
                viewInRecycler(position = 1, itemId = subTitleItem).checkText("fact about 2")
                viewInRecycler(position = 0, itemId = titleItem).checkText("1")
                viewInRecycler(position = 0, itemId = subTitleItem).checkText("fact about 1")
            }
        }
    }
}