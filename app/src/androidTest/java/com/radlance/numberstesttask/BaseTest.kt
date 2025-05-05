package com.radlance.numberstesttask

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

abstract class BaseTest {

    protected fun Int.view(): ViewInteraction = onView(withId(this))

    protected fun ViewInteraction.typeText(value: String) {
        perform(ViewActions.typeText(value))
        closeSoftKeyboard()
    }

    protected fun ViewInteraction.checkText(value: String): ViewInteraction {
        return check(matches(withText(value)))
    }

    protected fun ViewInteraction.click(): ViewInteraction = perform(ViewActions.click())

    protected fun Int.viewInRecycler(position: Int, itemId: Int): ViewInteraction {
        return onView(RecyclerViewMatcher(this).atPosition(position, itemId))
    }
}