package ru.trfx.apps.forecaster.data.weather

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoWeatherRepository : WeatherRepository {
    companion object {
        private const val HOURLY_VARS = "temperature_2m,relative_humidity_2m," +
                "pressure_msl,precipitation_probability,weather_code,wind_speed_10m"
        private const val DAILY_VARS = "temperature_2m_min,temperature_2m_max,weather_code"
        private const val UNITS = "temperature_unit=celsius&wind_speed_unit=kmh"
    }

    @GET("https://api.open-meteo.com/v1/forecast?$UNITS&current=$HOURLY_VARS")
    override suspend fun getCurrentWeather(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
    ): CurrentWeatherResponse

    @GET("https://api.open-meteo.com/v1/forecast?forecast_days=2&$UNITS&hourly=$HOURLY_VARS")
    override suspend fun getHourlyWeather(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
    ): HourlyWeatherResponse

    @GET("https://api.open-meteo.com/v1/forecast?forecast_days=7&$UNITS&daily=$DAILY_VARS")
    override suspend fun getDailyWeather(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
    ): DailyWeatherResponse
}
