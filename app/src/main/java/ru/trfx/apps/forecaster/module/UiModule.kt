package ru.trfx.apps.forecaster.module

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.trfx.apps.forecaster.ui.hourly.HourlyDetailsViewModel
import ru.trfx.apps.forecaster.ui.location.LocationViewModel
import ru.trfx.apps.forecaster.ui.main.MainViewModel

val uiModule = module {
    viewModelOf(::LocationViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::HourlyDetailsViewModel)
}
