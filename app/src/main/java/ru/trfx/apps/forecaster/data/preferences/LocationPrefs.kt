package ru.trfx.apps.forecaster.data.preferences

data class LocationPrefs(
    val name: String,
    val country: String,
    val latitude: Float,
    val longitude: Float,
) {
    companion object {
        const val NAME_KEY = "location.name"
        const val COUNTRY_KEY = "location.country"
        const val LATITUDE_KEY = "location.latitude"
        const val LONGITUDE_KEY = "location.longitude"
    }
}
