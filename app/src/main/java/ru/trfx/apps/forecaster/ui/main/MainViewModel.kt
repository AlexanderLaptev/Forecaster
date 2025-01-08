package ru.trfx.apps.forecaster.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.trfx.apps.forecaster.data.preferences.PreferencesRepository
import ru.trfx.apps.forecaster.data.weather.CurrentWeatherResponse
import ru.trfx.apps.forecaster.data.weather.WeatherRepository

class MainViewModel(
    private val weatherRepo: WeatherRepository,
    private val prefsRepo: PreferencesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenWeatherUiState())
    val uiState = _uiState.asStateFlow()

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            val locationPrefs = prefsRepo.loadLocation()!!
            val response = weatherRepo.getCurrentWeather(
                locationPrefs.latitude,
                locationPrefs.longitude
            )
            _uiState.update { response.toUiState() }
        }
    }

    private fun CurrentWeatherResponse.toUiState(): MainScreenWeatherUiState {
        val weather = this.current!!
        return MainScreenWeatherUiState(
            WeatherType.fromCode(weather.weatherCode!!),
            weather.temperature2m!!.toFloat(),
            weather.relativeHumidity2m!!,
            weather.windSpeed10m!!.toFloat(),
            weather.pressureMsl!!.toFloat(),
            weather.precipitationProbability!!,
        )
    }
}
