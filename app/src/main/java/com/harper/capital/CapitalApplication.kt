package com.harper.capital

import android.app.Application
import com.harper.capital.auth.authModule
import com.harper.capital.database.databaseModule
import com.harper.capital.main.overviewModule
import com.harper.capital.repository.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import timber.log.Timber

class CapitalApplication : Application() {
    private var koinApp: KoinApplication? = null

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        koinApp = startKoin {
            modules(appModule, overviewModule, authModule, databaseModule, repositoryModule)
            androidContext(applicationContext)
            androidLogger()
        }
    }

    override fun onLowMemory() {
        koinApp?.close()
        super.onLowMemory()
    }
}