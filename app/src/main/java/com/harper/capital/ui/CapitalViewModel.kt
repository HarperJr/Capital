package com.harper.capital.ui

import androidx.lifecycle.ViewModel
import com.harper.capital.navigation.GlobalRouter

class CapitalViewModel(private val router: GlobalRouter) : ViewModel() {
    private var isStarted: Boolean = false

    fun start() {
        if (!isStarted) {
            router.setRoot()
        }
        isStarted = true
    }
}
