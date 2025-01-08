package ru.trfx.apps.forecaster.data.preferences

import android.content.Context
import androidx.preference.PreferenceManager

class PreferencesRepository(
    private val context: Context,
) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = prefs.edit()

    fun saveLocation(locationPrefs: LocationPrefs) {
        with(editor) {
            putString(LocationPrefs.NAME_KEY, locationPrefs.name)
            putString(LocationPrefs.COUNTRY_KEY, locationPrefs.country)
            putFloat(LocationPrefs.LATITUDE_KEY, locationPrefs.latitude)
            putFloat(LocationPrefs.LONGITUDE_KEY, locationPrefs.longitude)
            apply()
        }
    }

    fun loadLocation(): LocationPrefs? {
        val name = prefs.getString(LocationPrefs.NAME_KEY, null)
        val country = prefs.getString(LocationPrefs.COUNTRY_KEY, null)
        val latitude = prefs.getFloat(LocationPrefs.LATITUDE_KEY, Float.NaN)
        val longitude = prefs.getFloat(LocationPrefs.LONGITUDE_KEY, Float.NaN)

        return if (
            name == null
            || country == null
            || latitude.isNaN()
            || longitude.isNaN()
        ) null else LocationPrefs(name, country, latitude, longitude)
    }
}
