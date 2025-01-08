package ru.trfx.apps.forecaster.ui.hourly

import ru.trfx.apps.forecaster.ui.main.WeatherType

data class HourlyDetailsItem(
    val time: String,
    val weatherType: WeatherType = WeatherType.Sunny,
    val temperature: Float = 0.0f,
    val humidity: Int = 0,
    val windSpeed: Float = 0.0f,
    val pressure: Float = 0.0f,
    val precipitationChance: Int = 0,
)
