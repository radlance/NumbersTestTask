package com.radlance.numberstesttask.main.presentation

import androidx.fragment.app.Fragment
import com.radlance.numberstesttask.details.presentation.NumberDetailsFragment
import com.radlance.numberstesttask.numbers.presentation.NumbersFragment

interface Screen {
    fun fragment(): Class<out Fragment>


    object Details : Screen {
        override fun fragment(): Class<out Fragment> = NumberDetailsFragment::class.java
    }

    object Numbers : Screen {
        override fun fragment(): Class<out Fragment> = NumbersFragment::class.java
    }
}