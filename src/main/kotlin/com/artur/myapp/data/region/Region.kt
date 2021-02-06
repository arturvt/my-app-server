package com.artur.myapp.data.region

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "regions")
data class Region(
        @JsonProperty("countryCode")val countryCode: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("isoCode") val isoCode: String,
        @JsonProperty("fipsCode") val fipsCode: String? = "",
        @JsonProperty("wikiDataId") val wikiDataId: String? = "",
)
