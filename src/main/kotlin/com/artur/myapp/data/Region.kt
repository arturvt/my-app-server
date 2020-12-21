package com.artur.myapp.data

import com.fasterxml.jackson.annotation.JsonProperty

data class RegionRequest(
        @JsonProperty("data")val data: List<Region>)

data class Region(
        @JsonProperty("countryCode")val countryCode: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("isoCode") val isoCode: String,
        @JsonProperty("fipsCode") val fipsCode: String? = "",
        @JsonProperty("wikiDataId") val wikiDataId: String? = "",
)
