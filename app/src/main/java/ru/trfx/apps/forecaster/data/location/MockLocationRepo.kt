package ru.trfx.apps.forecaster.data.location

import kotlinx.coroutines.delay
import kotlin.random.Random

object MockLocationRepo : LocationRepository {
    override suspend fun searchLocations(query: String, language: String): LocationSearchResults {
        delay(40)
        if (query.isBlank()) return LocationSearchResults()
        if (query.length > 10) return LocationSearchResults()

        val results = mutableListOf<LocationSearchResult>()
        delay(Random.nextLong(20, 300))
        repeat(20) {
            results += LocationSearchResult(
                name = "$query #${it + 1}",
                admin1 = "Admin",
                country = "United States",
                latitude = Random.nextDouble(-90.0, 90.0),
                longitude = Random.nextDouble(0.0, 180.0),
            )
        }
        return LocationSearchResults(results)
    }
}
