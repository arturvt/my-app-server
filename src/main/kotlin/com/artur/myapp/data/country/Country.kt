package com.artur.myapp.data.country

import com.artur.myapp.data.region.Region
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "country")
data class Country(
    @Id @JsonProperty("code") val id: String,
    @JsonProperty("name") val name: String,
)

@Document(collection = "country")
data class CountryFull(
    @Id @JsonProperty("code") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("codeTwo") val codeTwo: String,
    @JsonProperty("wikiId") val wikiId: String,
    @JsonProperty("population") val population: Int,
    @JsonProperty("capital") val capital: String,
    @JsonProperty("flag") val flag: String,
    @JsonProperty("currency") val currency: Currency,
    @JsonProperty("location") val location: Location,
    @JsonProperty("regions") val regions: List<Region> = listOf(),
)

data class Currency(val name: String, val code: String)

data class Location(val location: String, val lat: String, val lon: String, val geo: String, val map: List<String>)
