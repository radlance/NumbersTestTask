package com.radlance.numberstesttask.main.presentation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

interface NavigationStrategy {
    fun navigate(supportFragmentManager: FragmentManager, containerId: Int)

    abstract class Abstract(
        protected open val screen: Screen
    ) : NavigationStrategy {
        override fun navigate(supportFragmentManager: FragmentManager, containerId: Int) {
            supportFragmentManager.beginTransaction().executeTransaction(containerId).commit()
        }

        protected abstract fun FragmentTransaction.executeTransaction(
            containerId: Int
        ): FragmentTransaction
    }

    data class Replace(override val screen: Screen) : Abstract(screen) {
        override fun FragmentTransaction.executeTransaction(
            containerId: Int
        ): FragmentTransaction = replace(
            containerId, screen.fragment().getDeclaredConstructor().newInstance()
        )
    }

    data class Add(override val screen: Screen) : Abstract(screen) {
        override fun FragmentTransaction.executeTransaction(
            containerId: Int
        ): FragmentTransaction = add(
            containerId, screen.fragment().getDeclaredConstructor().newInstance()
        ).addToBackStack(screen.fragment().simpleName)
    }
}