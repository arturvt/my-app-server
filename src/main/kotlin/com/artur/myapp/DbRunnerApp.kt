package com.artur.myapp

import com.artur.myapp.enum.RequestType
import com.artur.myapp.repository.CountryRepository
import com.artur.myapp.service.CountryService
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

    @Autowired
    private lateinit var countryService: CountryService;

    override fun run(vararg args: String?) {
        logger.info("-------------------------------")
        logger.info("Runner Code")
        logger.info("#####")
        logger.info("Countries: ${countryRepository.count()}")

        countryService.jobSearchCountries(listOf("US"), RequestType.REGION)
        logger.info("Done request")

    }

}

fun main(args: Array<String>) {
    SpringApplication.run(DbRunnerApp::class.java)
}
