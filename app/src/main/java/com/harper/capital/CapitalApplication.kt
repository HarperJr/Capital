package com.harper.capital

import android.app.Application
import com.harper.capital.ui.di.application
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class CapitalApplication : Application() {
    private var koinApp: KoinApplication? = null

    override fun onCreate() {
        super.onCreate()

        koinApp = startKoin {
            modules(application)
        }
    }

    override fun onLowMemory() {
        koinApp?.close()
        super.onLowMemory()
    }
}