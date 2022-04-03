package com.harper.capital.analytics.model

sealed class AnalyticsEvent {

    class PageSelect(val index: Int) : AnalyticsEvent()

    object BackClick : AnalyticsEvent()
}
