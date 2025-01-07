package ru.trfx.apps.forecaster.data.location

import com.google.gson.annotations.SerializedName

data class LocationSearchResults(
    @SerializedName("results") val results: List<LocationSearchResult> = emptyList()
)

data class LocationSearchResult(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("elevation") var elevation: Int? = null,
    @SerializedName("feature_code") var featureCode: String? = null,
    @SerializedName("country_code") var countryCode: String? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("population") var population: Int? = null,
    @SerializedName("postcodes") var postcodes: List<String> = emptyList(),
    @SerializedName("country_id") var countryId: Int? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("admin1") var admin1: String? = null,
    @SerializedName("admin2") var admin2: String? = null,
    @SerializedName("admin3") var admin3: String? = null,
    @SerializedName("admin4") var admin4: String? = null,
    @SerializedName("admin1_id") var admin1Id: Int? = null,
    @SerializedName("admin2_id") var admin2Id: Int? = null,
    @SerializedName("admin3_id") var admin3Id: Int? = null,
    @SerializedName("admin4_id") var admin4Id: Int? = null,
)
