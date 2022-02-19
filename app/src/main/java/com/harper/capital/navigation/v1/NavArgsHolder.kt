package com.harper.capital.navigation.v1

import android.os.Bundle
import androidx.navigation.NamedNavArgument

interface NavArgsHolder<Data> {
    val navArguments: List<NamedNavArgument>

    fun getArguments(param: Data): Map<String, Any?>

    fun getData(args: Bundle): Data
}
