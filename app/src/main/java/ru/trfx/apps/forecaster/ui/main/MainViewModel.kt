package ru.trfx.apps.forecaster.ui.main

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.trfx.apps.forecaster.data.preferences.PreferencesRepository
import ru.trfx.apps.forecaster.data.weather.WeatherRepository
import java.util.Locale

class MainViewModel(
    private val weatherRepo: WeatherRepository,
    private val prefsRepo: PreferencesRepository,
) : ViewModel() {
    companion object {
        private const val DAILY_DATE_PATTERN = "MMM dd"

        @SuppressLint("SimpleDateFormat")
        private val isoDateFormat = SimpleDateFormat("yyyy-MM-dd")
        private val dailyDateFormat = SimpleDateFormat(DAILY_DATE_PATTERN, Locale.US)
    }

    private val _uiState = MutableStateFlow(MainScreenWeatherUiState())
    val uiState = _uiState.asStateFlow()

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            val locationPrefs = prefsRepo.loadLocation()!!
            val currentResult = weatherRepo.getCurrentWeather(
                locationPrefs.latitude,
                locationPrefs.longitude
            )
            val dailyResult = weatherRepo.getDailyWeather(
                locationPrefs.latitude,
                locationPrefs.longitude,
            )

            val daily = dailyResult.daily!!
            val dailyForecast = mutableListOf<DailyWeatherUiState>()
            repeat(daily.time.size) {
                val date = isoDateFormat.parse(daily.time[it])
                dailyForecast += DailyWeatherUiState(
                    dailyDateFormat.format(date),
                    WeatherType.fromCode(daily.weatherCode[it]),
                    daily.temperature2mMax[it].toFloat(),
                    daily.temperature2mMin[it].toFloat(),
                )
            }

            _uiState.update {
                val weather = currentResult.current!!

                MainScreenWeatherUiState(
                    WeatherType.fromCode(weather.weatherCode!!),
                    weather.temperature2m!!.toFloat(),
                    weather.relativeHumidity2m!!,
                    weather.windSpeed10m!!.toFloat(),
                    weather.pressureMsl!!.toFloat(),
                    weather.precipitationProbability!!,
                    dailyForecast,
                )
            }
        }
    }
}
