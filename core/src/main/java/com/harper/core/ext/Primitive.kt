package com.harper.core.ext

inline fun <reified T : Any> Any?.tryCast(): T? = this as? T

inline fun <reified T : Any> Any?.cast(): T = this as T

inline fun <reified T : Any> T?.orElse(default: T): T = this ?: default

inline fun <reified T : Any> T?.orElse(default: () -> T): T = this ?: default.invoke()

inline fun <reified T : Any> T?.orElseNullable(default: () -> T?): T? = this ?: default.invoke()
