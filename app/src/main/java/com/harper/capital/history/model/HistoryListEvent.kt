package com.harper.capital.history.model

sealed class HistoryListEvent {

    object BackClick : HistoryListEvent()

    object FilterItemClick : HistoryListEvent()
}
