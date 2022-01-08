package com.harper.capital.navigation

import com.github.terrakok.cicerone.Router

class GlobalRouterImpl : Router(), GlobalRouter {

    override fun setSignInAsRoot() = newRootScreen(Screens.signIn())

    override fun setMainAsRoot() = newRootScreen(Screens.main())

    override fun navigateToAddAsset() = navigateTo(Screens.assetAdd())

    override fun back() = exit()
}