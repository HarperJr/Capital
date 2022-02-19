package com.harper.capital.navigation

import com.github.terrakok.cicerone.Screen

interface ComposableScreen : Screen {

    companion object {

        operator fun invoke(key: String, arguments: Map<String, Any?> = emptyMap()): ComposableScreen = object : ComposableScreen {
            override val screenKey: String
                get() = buildString {
                    append(key)
                    if (arguments.isNotEmpty()) {
                        append(
                            arguments.asIterable()
                                .filter { it.value != null }
                                .joinToString(prefix = "?", separator = "&") { (paramKey, value) ->
                                    "$paramKey=$value"
                                }
                        )
                    }
                }
        }
    }
}
