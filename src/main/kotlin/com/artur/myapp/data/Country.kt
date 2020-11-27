package com.artur.myapp.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "countries")
data class Country(
        @Id val id: String,
        val name: String,
        val code: String,
        val currencyCodes: List<String>? = listOf(),
        val numRegions: Int? = 0,
        val wikiData: String? = "",
        val flagImageUri: String? = "")
