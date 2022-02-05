package com.harper.capital.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

class GlobalNavigator(
    activity: FragmentActivity,
    @IdRes containerId: Int,
    fragmentManager: FragmentManager
) : AppNavigator(activity, containerId, fragmentManager) {

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        ScreenAppearanceAnimations[screen.screenKey]
            ?.animateAppearance(transaction = fragmentTransaction)
    }
}
