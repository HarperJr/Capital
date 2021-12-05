package com.harper.core.ui

interface EventSender<Event : Any> {
    val viewModel: EventObserver<Event>

    fun send(event: Event) = viewModel.onEvent(event)
}

class MockEventSender<Event : Any> : EventSender<Event> {

    override val viewModel: EventObserver<Event> = EventObserver {}
}
