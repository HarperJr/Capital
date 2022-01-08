package com.harper.capital.navigation

interface GlobalRouter {

    fun setSignInAsRoot()

    fun setMainAsRoot()

    fun navigateToAddAsset()

    fun back()
}