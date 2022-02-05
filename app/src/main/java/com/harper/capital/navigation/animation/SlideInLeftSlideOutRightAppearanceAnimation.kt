package com.harper.capital.navigation.animation

import androidx.fragment.app.FragmentTransaction

class SlideInLeftSlideOutRightAppearanceAnimation : ScreenAppearanceAnimation {

    override fun animateAppearance(transaction: FragmentTransaction) {
        transaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right,
            0,
            android.R.anim.slide_out_right
        )
    }
}
