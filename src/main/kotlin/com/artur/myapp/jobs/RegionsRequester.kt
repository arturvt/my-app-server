package com.artur.myapp.jobs

import com.artur.myapp.data.region.Region
import com.artur.myapp.jobs.dto.RegionDetailRequest
import com.artur.myapp.jobs.dto.RegionRequest
import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.random.Random

class RegionsRequester {

    private val HOST = "https://wft-geo-db.p.rapidapi.com"
    private val regionPath = "$HOST/v1/geo/countries/%s/regions"
    private var regions: List<Region> = listOf()

    private fun getRegionRequest(countryKey: String, href: String?): RegionRequest? {
        val url = if (href.isNullOrBlank()) {
            regionPath.format(countryKey)
        } else {
            HOST + href
        }
        println("URL: $url")
        val response = doRequest(url)
        if (response != null) {
            if (response.statusCode() == 200) {
                return ObjectMapper().readValue(response.body(), RegionRequest::class.java)
            } else if (response.statusCode() == 429) {
                println("Waiting longer time")
                Thread.sleep(1500L)
            }
            println("Request error; Code: ${response.statusCode()}")
            println("Message: ${response.body()}")
        } else {
            println("No response")
        }
        return null
    }

    private fun getRegionFullRequest(countryKey: String, regionKey: String): RegionDetailRequest? {
        val url = regionPath.format(countryKey) + '/' + regionKey
        println("URL: $url")
        val response = doRequest(url)
        if (response != null) {
            if (response.statusCode() == 200) {
                return ObjectMapper().readValue(response.body(), RegionDetailRequest::class.java)
            } else if (response.statusCode() == 429) {
                println("Waiting longer time")
                Thread.sleep(1500L)
            }
            println("Request error; Code: ${response.statusCode()}")
            println("Message: ${response.body()}")
        } else {
            println("No response")
        }
        return null
    }

    fun performRegionFullRequest(countryKey: String, regionKey: String): RegionDetailRequest? {
        println("FullRegion $countryKey - $regionKey")
        return getRegionFullRequest(countryKey, regionKey)
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
            val nextLink = regionRequest.links?.find { it.rel == "next" }
            if (nextLink != null) {
                val sleepSeconds = Random.nextInt(1, 3)
                val longVal = 1500L
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
