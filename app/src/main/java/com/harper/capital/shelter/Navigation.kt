package com.harper.capital.shelter

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.harper.capital.navigation.ScreenKey
import com.harper.core.component.transition.Transitions
import com.harper.core.component.transition.fadeTransition
import com.harper.core.component.transition.verticalSlideTransition
import com.harper.core.navigation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.shelter() {
    composable(
        route = ScreenKey.SHELTER.route,
        transitions = Transitions(enterExit = verticalSlideTransition() + fadeTransition())
    ) {
        ShelterNavHolder()
    }
}
