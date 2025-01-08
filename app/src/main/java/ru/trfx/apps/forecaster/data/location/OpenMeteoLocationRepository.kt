package ru.trfx.apps.forecaster.data.location

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoLocationRepository : LocationRepository {
    @GET("https://geocoding-api.open-meteo.com/v1/search?count=20")
    override suspend fun searchLocations(
        @Query("name") query: String,
        @Query("language") language: String
    ): LocationSearchResults
}
