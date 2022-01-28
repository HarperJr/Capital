package com.harper.core.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

fun <T, R> Flow<T>.zipWith(other: Flow<R>) = zip(other) { t1, t2 -> t1 to t2 }

fun <T> Flow<T?>.onNullDefault(default: T) = this.map { it ?: default }

inline fun <reified T> List<Flow<T>>.combineToList(): Flow<List<T>> = combine(this) { it.toList() }