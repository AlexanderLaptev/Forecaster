package ru.trfx.apps.forecaster.ui.location

data class LocationItem(
    val name: String,
    val admin: String,
    val country: String,
) {
    val details = "$admin, $country"
}
