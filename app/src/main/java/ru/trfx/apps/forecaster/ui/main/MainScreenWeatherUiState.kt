package ru.trfx.apps.forecaster.ui.main

enum class WeatherType {
    Sunny,
    Cloudy,
    PartlyCloudy,
    Raining,
    Snowing,
}

data class MainScreenWeatherUiState(
    val weatherType: WeatherType = WeatherType.Sunny,
    val temperature: Float = 0.0f,
    val humidity: Int = 0,
    val windSpeed: Float = 0.0f,
    val pressure: Float = 0.0f,
    val precipitationChance: Int = 0,
)
