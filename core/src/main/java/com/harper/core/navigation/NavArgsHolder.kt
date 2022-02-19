package com.harper.core.navigation

import org.koin.ext.getFullName
import kotlin.reflect.KClass

class NavArgsHolder(private val values: List<Any?> = emptyList()) {
    inline operator fun <reified T> component1(): T = elementAt(0, T::class)
    inline operator fun <reified T> component2(): T = elementAt(1, T::class)
    inline operator fun <reified T> component3(): T = elementAt(2, T::class)
    inline operator fun <reified T> component4(): T = elementAt(3, T::class)
    inline operator fun <reified T> component5(): T = elementAt(4, T::class)

    fun <T> elementAt(i: Int, clazz: KClass<*>): T =
        if (values.size > i) values[i] as T else throw NoSuchElementException(
            "Can't get injected parameter #$i from $this for type '${clazz.getFullName()}'"
        )
}
