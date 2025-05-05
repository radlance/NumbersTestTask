package com.radlance.numberstesttask

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.radlance.numberstesttask.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckItemReplaceTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_history() {
        //1. enter 1
        onView(withId(R.id.inputEditText)).perform(typeText("1"))
        closeSoftKeyboard()
        onView(withId(R.id.factButton)).perform(click())

        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.titleTextView))
            .check(matches(withText("1")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.subTitleTextView))
            .check(matches(withText("fact about 1")))

        //2. enter 2
        onView(withId(R.id.inputEditText)).perform(typeText("2"))
        closeSoftKeyboard()
        onView(withId(R.id.factButton)).perform(click())

        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.titleTextView))
            .check(matches(withText("2")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.subTitleTextView))
            .check(matches(withText("fact about 2")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.titleTextView))
            .check(matches(withText("1")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.subTitleTextView))
            .check(matches(withText("fact about 1")))

        //3. enter 1 again
        onView(withId(R.id.inputEditText)).perform(typeText("1"))
        closeSoftKeyboard()
        onView(withId(R.id.factButton)).perform(click())

        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.titleTextView))
            .check(matches(withText("2")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.subTitleTextView))
            .check(matches(withText("fact about 2")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.titleTextView))
            .check(matches(withText("1")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.subTitleTextView))
            .check(matches(withText("fact about 1")))
    }
}