package ru.trfx.apps.forecaster.data.weather

interface WeatherRepository {
    suspend fun getCurrentWeather(
        latitude: Float,
        longitude: Float,
    ): CurrentWeatherResponse

    suspend fun getHourlyWeather(
        latitude: Float,
        longitude: Float,
    ): HourlyWeatherResponse

    suspend fun getDailyWeather(
        latitude: Float,
        longitude: Float,
    ): DailyWeatherResponse
}
