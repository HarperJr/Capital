package com.harper.capital.navigation

import com.github.terrakok.cicerone.Command

internal class BackWithResult<T : Any>(val key: String, val result: T) : Command
