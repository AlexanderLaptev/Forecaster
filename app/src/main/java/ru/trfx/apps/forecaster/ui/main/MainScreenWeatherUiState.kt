package ru.trfx.apps.forecaster.ui.main

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
    }
}

data class MainScreenWeatherUiState(
    val weatherType: WeatherType = WeatherType.Sunny,
    val temperature: Float = 0.0f,
    val humidity: Int = 0,
    val windSpeed: Float = 0.0f,
    val pressure: Float = 0.0f,
    val precipitationChance: Int = 0,
)
