package com.artur.myapp.data.region

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "region")
data class CountryRegion(
    @Id val id: String,
    @JsonProperty("regions") var regions: List<RegionFull>
)

data class RegionFull(
    @JsonProperty("isoCode") val isoCode: String,
    @JsonProperty("countryCode") val countryCode: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("fipsCode") val fipsCode: String? = "",
    @JsonProperty("wikiDataId") val wikiDataId: String? = "",
    @JsonProperty("numCities") val numCities: Int,
    @JsonProperty("capital") val capital: String?,
)

fun main() {
    println("Teste")
    val str = " {\n" +
            "        \"capital\": \"Montgomery\",\n" +
            "        \"countryCode\": \"US\",\n" +
            "        \"fipsCode\": \"01\",\n" +
            "        \"isoCode\": \"AL\",\n" +
            "        \"name\": \"Alabama\",\n" +
            "        \"numCities\": 1051,\n" +
            "        \"wikiDataId\": \"Q173\"\n" +
            "    }"
//    val reg = ObjectMapper().readValue(str, RegionFull::class.java)
    val reg = ObjectMapper()
        .readValue(str, RegionFull::class.java)
    println(reg)
    val countryRegion = CountryRegion("US", listOf(reg))
    println(countryRegion)
}
