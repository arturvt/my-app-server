package com.artur.myapp.data.country

import com.artur.myapp.data.region.Region
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "country")
data class CountryFull(
        @Id @JsonProperty("code")val id: String,
        @JsonProperty("name")val name: String,
        @JsonProperty("capital") val capital: String? = "unknown",
        @JsonProperty("currencyCodes") val currencyCodes: List<String>? = listOf(),
        @JsonProperty("numRegions")val numRegions: Int = 0,
        @JsonProperty("wikiData")val wikiData: String? = "",
        @JsonProperty("wikiDataId")val wikiDataId: String? = "",
        @JsonProperty("flagImageUri")val flagImageUri: String? = "",
        @JsonProperty("region")var region: List<Region>?)

