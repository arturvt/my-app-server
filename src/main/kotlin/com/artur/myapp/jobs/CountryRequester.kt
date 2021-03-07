package com.artur.myapp.jobs

import com.artur.myapp.jobs.dto.CountryDetailRequest
import com.artur.myapp.jobs.dto.CountryRequest
import com.fasterxml.jackson.databind.ObjectMapper

private const val countryURL = "https://wft-geo-db.p.rapidapi.com/v1/geo/countries?offset=%d&limit=%d"
private const val countryDetailURL = "https://wft-geo-db.p.rapidapi.com/v1/geo/countries/%s"

private const val LIMIT = 5

private fun doCountryRequest(page: Int): CountryRequest? {
    val offSet = page * LIMIT
    val url = countryURL.format(offSet, LIMIT)

    val response = doRequest(url)

    if (response != null) {
        if (response.statusCode() == 200) {
            return ObjectMapper().readValue(response.body(), CountryRequest::class.java)
        } else if (response.statusCode() == 429) {
            println("Waiting longer time")
            Thread.sleep(3000L)
        }
        println("Request error; Code: ${response.statusCode()}")
        println("Message: ${response.body()}")
    } else {
        println("No Response!")
    }
    return null
}

private fun doCountryDetailRequest(code: String): CountryDetailRequest? {
    val url = countryDetailURL.format(code)
    val response = doRequest(url)
    if (response != null) {
        if (response.statusCode() == 200) {
            return ObjectMapper().readValue(response.body(), CountryDetailRequest::class.java)
        }
        println("Request error; Code: ${response.statusCode()}")
        println("Message: ${response.body()}")
    } else {
        println("No Response!")
    }
    return null
}

fun performCountryFullRequest(code: String): CountryDetailRequest? {
    println("CountryFullRequest page: $code")
    return doCountryDetailRequest(code)
}

fun performRequest(page: Int): CountryRequest? {
    println("CountryRequest page: $page")
    return doCountryRequest(page)
}
