package ru.trfx.apps.forecaster.module

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.trfx.apps.forecaster.ui.location.LocationViewModel

val uiModule = module {
    viewModelOf(::LocationViewModel)
}