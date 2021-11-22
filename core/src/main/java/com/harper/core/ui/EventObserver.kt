package com.harper.core.ui

fun interface EventObserver<Event : Any> {

    fun onEvent(event: Event)
}
