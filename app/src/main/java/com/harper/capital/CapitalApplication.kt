package com.harper.capital

import android.app.Application
import android.os.StrictMode
import com.harper.capital.accounts.accountsModule
import com.harper.capital.analytics.analyticsModule
import com.harper.capital.asset.assetModule
import com.harper.capital.auth.authModule
import com.harper.capital.liability.liabilityModule
import com.harper.capital.database.databaseModule
import com.harper.capital.history.historyModule
import com.harper.capital.main.mainModule
import com.harper.capital.network.networkModule
import com.harper.capital.repository.repositoryModule
import com.harper.capital.settings.settingsModule
import com.harper.capital.shelter.shelterModule
import com.harper.capital.transaction.transactionModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import timber.log.Timber

class CapitalApplication : Application() {
    private var koinApp: KoinApplication? = null

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            val threadPolicy = StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork()
                .penaltyLog()
                .build()
            StrictMode.setThreadPolicy(threadPolicy)
            val vmPolicy = StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
            StrictMode.setVmPolicy(vmPolicy)
        }

        Timber.plant(Timber.DebugTree())
        startKoin()
    }

    private fun startKoin() {
        koinApp = startKoin {
            modules(
                appModule,
                mainModule,
                authModule,
                assetModule,
                transactionModule,
                liabilityModule,
                historyModule,
                settingsModule,
                databaseModule,
                networkModule,
                repositoryModule,
                shelterModule,
                analyticsModule,
                accountsModule
            )
            androidContext(applicationContext)
            androidLogger()
        }
    }

    override fun onLowMemory() {
        koinApp?.close()
        super.onLowMemory()
    }

    override fun onTerminate() {
        koinApp?.close()
        super.onTerminate()
    }
}
