package com.radlance.numberstesttask

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPosition(position: Int, targetViewId: Int = -1) = object : TypeSafeMatcher<View>() {

        var resources: Resources? = null
        var childView: View? = null

        override fun describeTo(description: Description) {
            var isDescription = recyclerViewId.toString()
            resources?.let {
                isDescription = try {
                     it.getResourceName(recyclerViewId)
                } catch (e: Resources.NotFoundException) {
                    "$recyclerViewId (resource name not found)"
                }
            }
            description.appendText("RecyclerView with id: $isDescription at position: $position")
        }

        override fun matchesSafely(item: View): Boolean {
            resources = item.resources

            if (childView == null) {
                val recyclerView = item.rootView.findViewById<RecyclerView>(recyclerViewId)
                if (recyclerView?.id == recyclerViewId) {
                    val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                    childView = viewHolder?.itemView
                } else {
                    return false
                }
            }

            return if (targetViewId == -1) {
                item == childView
            } else {
                item === childView?.findViewById<View>(targetViewId)
            }
        }
    }
}