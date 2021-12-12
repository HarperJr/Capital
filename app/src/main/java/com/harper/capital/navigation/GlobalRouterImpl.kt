package com.harper.capital.navigation

import com.github.terrakok.cicerone.Router

class GlobalRouterImpl : Router(), GlobalRouter {

    override fun setRoot() = newRootScreen(Screens.overview())

    override fun navigateToAddAsset() = navigateTo(Screens.assetAdd())

    override fun back() = exit()
}