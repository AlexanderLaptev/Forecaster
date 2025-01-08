package ru.trfx.apps.forecaster.ui.main

import ru.trfx.apps.forecaster.R

enum class WeatherType(val key: String) {
    Sunny("sunny"),
    Cloudy("cloudy"),
    PartlyCloudy("partly\ncloudy"),
    Raining("raining"),
    Snowing("snowing");

    companion object {
        fun fromCode(wmoCode: Int) = when (wmoCode) {
            0, 1 -> Sunny
            2 -> PartlyCloudy
            3 -> Cloudy
            51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> Raining
            71, 73, 75, 77, 85, 86 -> Snowing
            else -> Cloudy
        }

        private val toDrawableMap = mapOf(
            Sunny to R.drawable.sunny_24px,
            Cloudy to R.drawable.cloud_24px,
            PartlyCloudy to R.drawable.partly_cloudy_day_24px,
            Raining to R.drawable.rainy_24px,
            Snowing to R.drawable.weather_snowy_24px,
        )
    }

    val drawable: Int get() = toDrawableMap[this]!!
}

data class MainScreenWeatherUiState(
    val weatherType: WeatherType = WeatherType.Sunny,
    val temperature: Float = 0.0f,
    val humidity: Int = 0,
    val windSpeed: Float = 0.0f,
    val pressure: Float = 0.0f,
    val precipitationChance: Int = 0,
    val dailyForecast: List<DailyWeatherUiState> = emptyList(),
)

data class DailyWeatherUiState(
    val date: String,
    val weatherType: WeatherType,
    val maxTemp: Float,
    val minTemp: Float,
)
