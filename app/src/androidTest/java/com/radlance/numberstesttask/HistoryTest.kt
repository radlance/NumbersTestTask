package com.radlance.numberstesttask

import org.junit.Test

class HistoryTest : BaseTest() {

    @Test
    fun test_number() {
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

    @Test
    fun test_random() {
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

            randomButton.view().click()

            with(recycler) {
                viewInRecycler(position = 1, itemId = titleItem).checkText("2")
                viewInRecycler(position = 1, itemId = subTitleItem).checkText("fact about 2")
                viewInRecycler(position = 0, itemId = titleItem).checkText("1")
                viewInRecycler(position = 0, itemId = subTitleItem).checkText("fact about 1")
            }
        }
    }
}