package com.artur.myapp

import com.artur.myapp.repository.country.CountryFullRepository
import com.artur.myapp.repository.country.CountryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DbRunnerApp : CommandLineRunner {

    private val logger: Logger = LoggerFactory.getLogger(DbRunnerApp::class.java)

    @Value("\${spring.data.mongodb.uri}")
    val mongoURL: String = ""

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Autowired
    private lateinit var countryFullRepository: CountryFullRepository


    override fun run(vararg args: String?) {
        logger.info("-------------------------------")
        logger.info("Runner Code")
        logger.info("#####")
        checkDb()
        logger.info("Done request")
    }

    fun checkDb() {
        logger.info("Checking db connection")
        logger.info("MongoHost: $mongoURL")
        logger.info("Number of items countryRepo: ${countryRepository.count()}")
    }

}

