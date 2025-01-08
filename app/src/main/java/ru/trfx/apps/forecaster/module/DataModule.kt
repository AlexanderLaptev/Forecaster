package ru.trfx.apps.forecaster.module

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.trfx.apps.forecaster.data.preferences.PreferencesRepository

val dataModule = module {
    singleOf(::PreferencesRepository)
}
