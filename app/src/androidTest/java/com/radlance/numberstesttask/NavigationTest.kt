package com.radlance.numberstesttask

import androidx.test.espresso.Espresso.pressBack
import org.junit.Test

class NavigationTest : BaseTest() {

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
            with(recycler) {
                viewInRecycler(position = 0, itemId = titleItem).checkText("10")
                viewInRecycler(position = 0, itemId = subTitleItem).checkText("fact about 10")
            }
        }
    }
}