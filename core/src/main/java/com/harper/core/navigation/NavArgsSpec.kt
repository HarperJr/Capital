package com.harper.core.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType

interface NavArgsSpec<in P> {
    val navArguments: List<NamedNavArgument>

    fun args(param: P): Map<String, Any?>

    fun getNavArgsHolder(args: Bundle): NavArgsHolder =
        navArguments
            .map { navArgument ->
                when (navArgument.argument.type) {
                    NavType.StringType -> args.getString(navArgument.name)
                    NavType.LongType -> args.getLong(navArgument.name)
                    NavType.IntType -> args.getInt(navArgument.name)
                    NavType.BoolType -> args.getBoolean(navArgument.name)
                    NavType.FloatType -> args.getFloat(navArgument.name)
                    else -> null
                }
            }
            .let { NavArgsHolder(it) }
}
