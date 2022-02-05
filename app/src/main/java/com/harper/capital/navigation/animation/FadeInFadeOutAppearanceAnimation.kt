package com.harper.capital.navigation.animation

import androidx.fragment.app.FragmentTransaction

class FadeInFadeOutAppearanceAnimation : ScreenAppearanceAnimation {

    override fun animateAppearance(transaction: FragmentTransaction) {
        transaction.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
    }
}
