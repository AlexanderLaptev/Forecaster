package ru.trfx.apps.forecaster

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.trfx.apps.forecaster.module.networkModule
import ru.trfx.apps.forecaster.module.uiModule

class ForecasterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ForecasterApplication)
            modules(networkModule, uiModule)
        }
    }
}
