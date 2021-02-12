package com.artur.myapp.data.country

import com.artur.myapp.data.region.Region
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "countries")
data class Country(
        @Id val id: String,
        val name: String,
        val code: String,
        var capital: String = "unknown",
        val currencyCodes: List<String>? = listOf(),
        val numRegions: Int = 0,
        val wikiData: String? = "",
        val flagImageUri: String? = "",
        var region: List<Region>?)

