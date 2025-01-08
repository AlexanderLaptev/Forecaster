package ru.trfx.apps.forecaster.data.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("generationtime_ms") var generationTimeMs: Double? = null,
    @SerializedName("utc_offset_seconds") var utcOffsetSeconds: Int? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("timezone_abbreviation") var timezoneAbbreviation: String? = null,
    @SerializedName("elevation") var elevation: Int? = null,
    @SerializedName("current_units") var currentUnits: CurrentUnits? = CurrentUnits(),
    @SerializedName("current") var current: CurrentWeather? = CurrentWeather()
)

data class CurrentUnits(
    @SerializedName("time") var time: String? = null,
    @SerializedName("interval") var interval: String? = null,
    @SerializedName("temperature_2m") var temperature2m: String? = null,
    @SerializedName("relative_humidity_2m") var relativeHumidity2m: String? = null,
    @SerializedName("pressure_msl") var pressureMsl: String? = null,
    @SerializedName("precipitation_probability") var precipitationProbability: String? = null,
    @SerializedName("weather_code") var weatherCode: String? = null,
    @SerializedName("wind_speed_10m") var windSpeed10m: String? = null,
)

data class CurrentWeather(
    @SerializedName("time") var time: String? = null,
    @SerializedName("interval") var interval: Int? = null,
    @SerializedName("temperature_2m") var temperature2m: Double? = null,
    @SerializedName("relative_humidity_2m") var relativeHumidity2m: Int? = null,
    @SerializedName("pressure_msl") var pressureMsl: Double? = null,
    @SerializedName("precipitation_probability") var precipitationProbability: Int? = null,
    @SerializedName("weather_code") var weatherCode: Int? = null,
    @SerializedName("wind_speed_10m") var windSpeed10m: Double? = null,
)

data class HourlyWeatherResponse(
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("generationtime_ms") var generationTimeMs: Double? = null,
    @SerializedName("utc_offset_seconds") var utcOffsetSeconds: Int? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("timezone_abbreviation") var timezoneAbbreviation: String? = null,
    @SerializedName("elevation") var elevation: Int? = null,
    @SerializedName("hourly_units") var hourlyUnits: HourlyUnits? = HourlyUnits(),
    @SerializedName("hourly") var hourly: HourlyWeather? = HourlyWeather(),
)

data class HourlyUnits(
    @SerializedName("time") var time: String? = null,
    @SerializedName("temperature_2m") var temperature2m: String? = null,
    @SerializedName("relative_humidity_2m") var relativeHumidity2m: String? = null,
    @SerializedName("pressure_msl") var pressureMsl: String? = null,
    @SerializedName("precipitation_probability") var precipitationProbability: String? = null,
    @SerializedName("weather_code") var weatherCode: String? = null,
    @SerializedName("wind_speed_10m") var windSpeed10m: String? = null,
)

data class HourlyWeather(
    @SerializedName("time") var time: List<String> = emptyList(),
    @SerializedName("temperature_2m") var temperature2m: List<Double> = emptyList(),
    @SerializedName("relative_humidity_2m") var relativeHumidity2m: List<Int> = emptyList(),
    @SerializedName("pressure_msl") var pressureMsl: List<Double> = emptyList(),
    @SerializedName("precipitation_probability") var precipitationProbability: List<Int> = emptyList(),
    @SerializedName("weather_code") var weatherCode: List<Int> = emptyList(),
    @SerializedName("wind_speed_10m") var windSpeed10m: List<Double> = emptyList(),
)

data class DailyWeatherResponse(
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("generationtime_ms") var generationTimeMs: Double? = null,
    @SerializedName("utc_offset_seconds") var utcOffsetSeconds: Int? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("timezone_abbreviation") var timezoneAbbreviation: String? = null,
    @SerializedName("elevation") var elevation: Int? = null,
    @SerializedName("daily_units") var dailyUnits: DailyUnits? = DailyUnits(),
    @SerializedName("daily") var daily: DailyWeather? = DailyWeather(),
)

data class DailyUnits(
    @SerializedName("time") var time: String? = null,
    @SerializedName("temperature_2m_min") var temperature2mMin: String? = null,
    @SerializedName("temperature_2m_max") var temperature2mMax: String? = null,
    @SerializedName("weather_code") var weatherCode: String? = null,
)

data class DailyWeather(
    @SerializedName("time") var time: List<String> = arrayListOf(),
    @SerializedName("temperature_2m_min") var temperature2mMin: List<Double> = emptyList(),
    @SerializedName("temperature_2m_max") var temperature2mMax: List<Double> = emptyList(),
    @SerializedName("weather_code") var weatherCode: List<Int> = emptyList(),
)
