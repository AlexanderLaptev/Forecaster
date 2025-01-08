package ru.trfx.apps.forecaster.ui.hourly

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
import ru.trfx.apps.forecaster.ui.main.WeatherType

class HourlyDetailsViewModel(
    private val weatherRepo: WeatherRepository,
    private val prefsRepo: PreferencesRepository,
) : ViewModel() {
    companion object {
        private val isoDateFormat = SimpleDateFormat("YYYY-MM-dd'T'HH:mm")
        private val timeFormat = SimpleDateFormat("HH:mm")
    }

    private val _hourlyDetails = MutableStateFlow<List<HourlyDetailsItem>>(emptyList())
    val hourlyDetails = _hourlyDetails.asStateFlow()

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            val locationPrefs = prefsRepo.loadLocation()!!
            val hourlyWeatherResponse = weatherRepo.getHourlyWeather(
                locationPrefs.latitude,
                locationPrefs.longitude,
            )

            val hourly = hourlyWeatherResponse.hourly!!
            val details = mutableListOf<HourlyDetailsItem>()
            repeat(hourly.time.size) {
                val time = isoDateFormat.parse(hourly.time[it])
                details += HourlyDetailsItem(
                    timeFormat.format(time),
                    WeatherType.fromCode(hourly.weatherCode[it]),
                    hourly.temperature2m[it].toFloat(),
                    hourly.relativeHumidity2m[it],
                    hourly.windSpeed10m[it].toFloat(),
                    hourly.pressureMsl[it].toFloat(),
                    hourly.precipitationProbability[it],
                )
            }
            _hourlyDetails.update { details }
        }
    }
}
