package com.artur.myapp

import com.artur.myapp.data.region.CountryRegion
import com.artur.myapp.jobs.RegionsRequester
import com.artur.myapp.jobs.performCountryFullRequest
import com.artur.myapp.jobs.performRequest
import com.artur.myapp.repository.RegionFullRepository
import com.artur.myapp.repository.country.CountryFullRepository
import com.artur.myapp.repository.country.CountryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DbRunnerApp : CommandLineRunner {

    private val logger: Logger = LoggerFactory.getLogger(DbRunnerApp::class.java)

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Autowired
    private lateinit var countryFullRepository: CountryFullRepository

    @Autowired
    private lateinit var regionFullRepository: RegionFullRepository


    override fun run(vararg args: String?) {
        logger.info("-------------------------------")
        logger.info("Runner Code")
        logger.info("#####")
//        fillFullRegions()  // Último País que parou é CH

        logger.info("Done request")
    }

    fun fillCountries() {
        for (i in 0..50) {
            Thread.sleep(1500)
            logger.info("Page: $i")
            val countryRequest = performRequest(i)
            if (countryRequest != null) {
                countryRepository.saveAll(countryRequest.data)
            } else {
                println("Stopped requests in page: $i")
            }
        }
    }

    fun fillFullCountry() {
        val allCountries = countryRepository.findAll()
        allCountries.forEachIndexed { index, country ->
            println("[$index][${country.id}] ${country.name}")
            Thread.sleep(1300)
            val countryFullReq = performCountryFullRequest(country.id)
            if (countryFullReq != null) {
                countryFullRepository.save(countryFullReq.data)
            }
        }
    }

    fun fillFullRegions() {

        val allCountries = countryFullRepository.findAll()
        allCountries.forEach { country ->
            logger.info("Full regions start: ${country.name}")
            val optionalCountryRegion = regionFullRepository.findById(country.id)
            val countryRegion: CountryRegion
            if (optionalCountryRegion.isEmpty) {
                countryRegion = CountryRegion(country.id, listOf())
            } else {
                println("Already on the list")
                return@forEach
            }

            country.region?.forEach {
                logger.info("[$country] [${it.isoCode}] ${it.name} ")
                Thread.sleep(1300)
                val fullRegionRequest = RegionsRequester().performRegionFullRequest(it.countryCode, it.isoCode)
                println(fullRegionRequest!!.data.capital)
                countryRegion.regions = countryRegion.regions + fullRegionRequest.data
                regionFullRepository.save(countryRegion)
            }
        }


    }

    fun fillRegions() {
        logger.info("Countries: ${countryFullRepository.count()}")
        val allCountries = countryFullRepository.findAll()
        for (countryFull in allCountries) {
            Thread.sleep(1300)
            val regionSize = countryFull.region?.size ?: 0
            println("[${countryFull.id}] ${countryFull.name} regions: ${countryFull.numRegions}  - stored: $regionSize")
            if (regionSize > 0 && countryFull.region.isNullOrEmpty() || countryFull.numRegions > regionSize) {
                println("Starting get regions for ${countryFull.name}")
                val regions = RegionsRequester().startFetching(countryFull.id)
                countryFull.region = regions
                countryFullRepository.save(countryFull)
            }
        }

    }

}

