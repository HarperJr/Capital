package com.harper.capital.ui.navigation

import com.github.terrakok.cicerone.Router

class GlobalRouter : Router() {

    fun setRoot() = newRootScreen(Screens.overview())
}