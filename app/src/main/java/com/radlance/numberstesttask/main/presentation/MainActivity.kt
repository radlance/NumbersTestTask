package com.radlance.numberstesttask.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.radlance.numberstesttask.R
import com.radlance.numberstesttask.main.sl.ProvideViewModel
import com.radlance.numberstesttask.numbers.presentation.NumbersFragment

class MainActivity : AppCompatActivity(), ShowFragment, ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            NavigationStrategy.Replace(NumbersFragment()).navigate(supportFragmentManager, R.id.container)
        }
    }

    override fun show(fragment: Fragment) {
        NavigationStrategy.Add(fragment).navigate(supportFragmentManager, R.id.container)
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T {
        return (application as ProvideViewModel).provideViewModel(clazz, owner)
    }
}

interface ShowFragment {

    fun show(fragment: Fragment)

    class Empty : ShowFragment {
        override fun show(fragment: Fragment) = Unit
    }
}