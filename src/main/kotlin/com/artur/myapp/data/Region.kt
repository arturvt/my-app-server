package com.artur.myapp.data

class RegionRequest(val data: List<Region>)

data class Region(
        val countryCode: String,
        val name: String,
        val isoCode: String,
        val fipsCode: String,
        val wikiDataId: String,
)
