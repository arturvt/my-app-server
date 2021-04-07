package com.artur.myapp.data.region

import com.fasterxml.jackson.annotation.JsonProperty

data class Region(
    @JsonProperty("isoCode") val isoCode: String,
    @JsonProperty("countryCode") val countryCode: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("fipsCode") val fipsCode: String? = "",
    @JsonProperty("wikiId") val wikiDataId: String? = "",
    @JsonProperty("numCities") val numCities: Int,
    @JsonProperty("capital") val capital: String?,
)
