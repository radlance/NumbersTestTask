package com.radlance.numberstesttask

import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.radlance.numberstesttask.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest : BaseTest() {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun details_navigation() {
        val numbersPage = NumbersPage()
        with(numbersPage) {
            input.view().typeText("10")
            factButton.view().click()

            with(recycler) {
                viewInRecycler(position = 0, itemId = titleItem).checkText("10")
                viewInRecycler(position = 0, itemId = subTitleItem).checkText("fact about 10")
                viewInRecycler(position = 0, itemId = subTitleItem).click()
            }
        }

        val detailsPage = DetailsPage()
        with(detailsPage) {
            details.view().checkText("10\n\nfact about 10")
        }

        pressBack()

        with(numbersPage) {
            titleItem.view().checkText("10")
            subTitleItem.view().checkText("fact about 10")
        }
    }
}