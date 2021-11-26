package com.harper.capital.ui.navigation

import com.github.terrakok.cicerone.Router
import com.harper.core.ui.navigation.GlobalRouter

class GlobalRouterImpl : Router(), GlobalRouter {

    override fun setRoot() = newRootScreen(Screens.overview())

    override fun navigateToAddAsset() = navigateTo(Screens.assetAdd())

    override fun back() = exit()
}