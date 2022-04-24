package com.harper.capital.network

import kotlin.reflect.KClass

/**
 * Annotation is used to mark interface which uses interceptor [value]
 */
@Target(AnnotationTarget.CLASS)
internal annotation class Intercept(val value: KClass<*>)
