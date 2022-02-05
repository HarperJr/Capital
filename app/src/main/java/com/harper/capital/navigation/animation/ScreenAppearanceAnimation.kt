package com.harper.capital.navigation.animation

import androidx.fragment.app.FragmentTransaction

fun interface ScreenAppearanceAnimation {

    fun animateAppearance(transaction: FragmentTransaction)
}
