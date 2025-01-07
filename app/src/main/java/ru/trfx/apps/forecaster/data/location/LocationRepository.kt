package ru.trfx.apps.forecaster.data.location

interface LocationRepository {
    suspend fun searchLocations(query: String, language: String): LocationSearchResults
}
