package com.artur.myapp

import com.artur.myapp.data.country.Country
import com.artur.myapp.jobs.RegionsRequester
import com.artur.myapp.repository.CountryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DbRunnerApp : CommandLineRunner {

    private val logger: Logger = LoggerFactory.getLogger(DbRunnerApp::class.java)

    @Autowired
    private lateinit var countryRepository: CountryRepository

    override fun run(vararg args: String?) {
        logger.info("-------------------------------")
        logger.info("Runner Code")
        logger.info("#####")
        logger.info("Countries: ${countryRepository.count()}")
        val allCountries = countryRepository.findAll()
        for (country in allCountries) {
            checkCountryRegions(country)
        }
        logger.info("Done request")

    }

    fun checkCountryRegions(country: Country) {
        val regionSize = country.region?.size ?: 0
        println("[${country.id}] ${country.name} regions: ${country.numRegions}  - stored: $regionSize")
        if (country.region.isNullOrEmpty() || country.numRegions > regionSize) {
            println("Starting get regions for ${country.name}")
            val regions = RegionsRequester().startFetching(country.code)
            country.region = regions
            countryRepository.save(country)
        }
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(DbRunnerApp::class.java)
}
