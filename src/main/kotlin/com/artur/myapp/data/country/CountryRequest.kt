package com.artur.myapp.data.country

import com.fasterxml.jackson.annotation.JsonProperty

data class CountryDataRequest(
        @JsonProperty("name") val name: String,
        @JsonProperty("code") val code: String,
        @JsonProperty("capital") val capital: String,
        @JsonProperty("currencyCodes") val currencyCodes: List<String>? = listOf(),
        @JsonProperty("numRegions") val numRegions: Int = 0,
        @JsonProperty("wikiData") val wikiData: String? = "",
        @JsonProperty("flagImageUri") val flagImageUri: String? = "")

data class CountryRequest(@JsonProperty("data")  val data: CountryDataRequest)
