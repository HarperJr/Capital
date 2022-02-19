package com.harper.capital.navigation

import androidx.navigation.NavController
import com.github.terrakok.cicerone.Back
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.Screen
import timber.log.Timber
import java.lang.ref.WeakReference

class ComposableNavigator : Navigator {
    private val navController: NavController
        get() = _navController.get() ?: throw IllegalStateException("Nav controller is not attached to navigator")
    private var _navController: WeakReference<NavController?> = WeakReference(null)
    private val localStackCopy: MutableList<String> = mutableListOf()

    fun attachNavController(navController: NavController) {
        this._navController = WeakReference(navController)
    }

    fun detachNavController() {
        this._navController.clear()
    }

    override fun applyCommands(commands: Array<out Command>) {
        copyStackToLocal()
        commands.forEach { command ->
            runCatching {
                applyCommand(command)
            }.onFailure {
                errorOnApplyCommand(command, it)
            }
        }
    }

    private fun copyStackToLocal() {
        localStackCopy.clear()
        navController.backQueue.forEach {
            it.destination.route?.let { route ->
                localStackCopy.add(route)
            }
        }
    }

    private fun applyCommand(command: Command) {
        when (command) {
            is Back -> applyBack()
            is Replace -> applyReplace(command.screen)
            is Forward -> applyForward(command.screen)
            is BackTo -> applyBackTo(command.screen)
        }
    }

    private fun applyReplace(screen: Screen) {
        Timber.d("Replace by route: ${screen.screenKey}")
        if (screen is ComposableScreen) {
            if (localStackCopy.isNotEmpty()) {
                navController.popBackStack()
                localStackCopy.removeAt(localStackCopy.lastIndex)
                navigateForward(screen)
            } else {
                navigateForward(screen)
            }
        } else {
            throw IllegalStateException("Screen must implement ComposableScreen interface")
        }
    }

    private fun applyForward(screen: Screen) {
        Timber.d("Forward by route: ${screen.screenKey}")
        if (screen is ComposableScreen) {
            navigateForward(screen)
        } else {
            throw IllegalStateException("Screen must implement ComposableScreen interface")
        }
    }

    private fun navigateForward(screen: ComposableScreen) {
        localStackCopy.add(screen.screenKey)
        navController.navigate(screen.screenKey)
    }

    private fun applyBackTo(screen: Screen?) {
        if (screen == null) {
            backToRoot()
        } else {
            val screenKey = screen.screenKey
            val index = localStackCopy.indexOfFirst { it == screenKey }
            if (index != -1) {
                val forRemove = localStackCopy.subList(index, localStackCopy.size)
                navController.popBackStack(forRemove.first(), inclusive = true)
                forRemove.clear()
            } else {
                backToRoot()
            }
        }
    }

    private fun applyBack() {
        if (localStackCopy.isNotEmpty()) {
            localStackCopy.removeAt(localStackCopy.lastIndex)
            navController.navigateUp()
        }
    }

    private fun backToRoot() {
        val rootBackStackEntry = localStackCopy.first()
        localStackCopy.removeAll { it != rootBackStackEntry }
        navController.popBackStack(rootBackStackEntry, inclusive = true)
    }

    private fun errorOnApplyCommand(command: Command, error: Throwable) {
        throw RuntimeException("Unable to apply command ${command.javaClass.simpleName} cause: ${error.message}")
    }
}
