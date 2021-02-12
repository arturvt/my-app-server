package com.artur.myapp.jobs

import com.artur.myapp.data.region.Region
import com.artur.myapp.jobs.dto.RegionRequest
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.random.Random

class RegionsRequester {

    private val HOST = "https://wft-geo-db.p.rapidapi.com"
    private val regionPath = "/v1/geo/countries/%s/regions"
    private var regions: List<Region> = listOf()

    private fun getRegionRequest(countryKey: String, href: String?): RegionRequest? {

        val url = if (href.isNullOrBlank()) {
            HOST + regionPath.format(countryKey)
        } else {
            HOST + href
        }
        println("URL: $url")

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .setHeader("Content-Type", "application/json")
            .setHeader("x-rapidapi-hos", "wft-geo-db.p.rapidapi.com")
            .setHeader("x-rapidapi-key", "4afD687vWcZfy5rQUP9BTvA255dNQ4Oi")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val objectMapper = ObjectMapper()

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), RegionRequest::class.java)
        } else if (response.statusCode() == 429) {
            println("Waiting longer time")
            Thread.sleep(3000L)
        }
        println("Request error; Code: ${response.statusCode()}")
        println("Message: ${response.body()}")
        return null


    }

    fun startFetching(countryKey: String): List<Region> {
        this.performRequest(countryKey, null)
        return this.regions
    }

    private fun performRequest(countryKey: String, href: String?) {
        println("Country: $countryKey - href: $href")
        val regionRequest = getRegionRequest(countryKey, href)
        if (regionRequest != null) {
            println(regionRequest.metadata)
            regions = regions.plus(regionRequest.data)
            val nextLink = regionRequest.links.find { it.rel == "next" }
            if (nextLink != null) {

                val sleepSeconds = Random.nextInt(2, 10)
                val longVal = sleepSeconds * 1500L
                println("Waiting $sleepSeconds secs -> Long $longVal")
                Thread.sleep(longVal)
                performRequest(countryKey, nextLink.href)
            } else {
                println("No more regions")
            }
        } else {
            println("No Region!")
        }
    }
}


fun main(args: Array<String>) {
    print("UO")
//    RegionsRequester().performRequest("AM", null)
    val list = RegionsRequester().startFetching("BR")
    println("Finish request. Total: ${list.size}")
//    RegionsRequester().performRequest("BR", null)
}
