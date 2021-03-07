package com.artur.myapp.data.country

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "countries")
class Country(
    @Id @JsonProperty("code") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("currencyCodes") val currencyCodes: List<String>? = listOf(),
    @JsonProperty("wikiDataId") val wikiData: String? = "",
)
