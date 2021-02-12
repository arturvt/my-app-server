package com.artur.myapp.service

import com.artur.myapp.data.country.Country
import com.artur.myapp.jobs.dto.CountryDetailDataRequest
import com.artur.myapp.jobs.dto.CountryDetailRequest
import com.artur.myapp.repository.CountryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random


@Service
class CountryService(
    private val countryRepository: CountryRepository,
    private val restTemplateBuilder: RestTemplateBuilder
) {

    private lateinit var httpEntity: HttpEntity<String>
    private val countriesUrl = "https://wft-geo-db.p.rapidapi.com/v1/geo/countries"

    init {
        val httpHeaders = HttpHeaders()
        httpHeaders["x-rapidapi-host"] = "wft-geo-db.p.rapidapi.com"
        httpHeaders["x-rapidapi-key"] = "4afD687vWcZfy5rQUP9BTvA255dNQ4Oi"
        httpHeaders["useQueryString"] = "true"
        httpEntity = HttpEntity<String>(httpHeaders)

    }

    private val restTemplate: RestTemplate = this.restTemplateBuilder.build()
    private val logger: Logger? = LoggerFactory.getLogger(CountryService::class.java)

    private val runningJobs: AtomicInteger = AtomicInteger(0)

    fun jobSearchCountries(listCodes: List<String>): String {
        if (listCodes.isNotEmpty()) {
            if (runningJobs.get() == 0) {
                Thread {
                    runningJobs.set(listCodes.size)
                    logger?.info("Starting requests for  ${runningJobs.get() + 1}")
                    listCodes.forEach {
                        val optionalCountry = countryRepository.findById(it.toUpperCase())
                        val sleepSeconds = Random.nextInt(2, 10)
                        val longVal = sleepSeconds * 1240L
                        println("Waiting $sleepSeconds secs -> Long $longVal")
                        Thread.sleep(longVal)
                        val country = doCountryRequest(it)

                        if (country == null) {
                            logger?.info("Failed to get content from: $it")
                            return@forEach
                        }

                        if (optionalCountry.isEmpty) {
                            val saved = countryRepository.save(country)
                            logger?.info("persisted:  ${saved.id}")
                        } else {
                            val storedCountry = optionalCountry.get()
                            storedCountry.capital = country.capital
                            val saved = countryRepository.save(storedCountry)
                            logger?.info("persisted:  ${saved.id}")
                        }
                        val remaining = runningJobs.decrementAndGet()
                        logger?.info("Remaining jobs: $remaining")
                    }

                }.start()
                return "Started ${listCodes.size}"
            } else {
                logger?.info("Current job list on going...")
                return "There's a job running..."
            }
        }
        return "List is empty"
    }

    private fun buildCountryObj(countryDetail: CountryDetailDataRequest): Country {
        return Country(
            id = countryDetail.code,
            code = countryDetail.code,
            name = countryDetail.name,
            capital = countryDetail.capital,
            currencyCodes = countryDetail.currencyCodes,
            flagImageUri = countryDetail.flagImageUri,
            numRegions = countryDetail.numRegions,
            wikiData = countryDetail.wikiData,
            region = listOf()
        )
    }

    private fun doCountryRequest(countryCode: String): Country? {
        logger?.info("Doing request for $countryCode")
        val url = "$countriesUrl/${countryCode}"

        try {
            val response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, CountryDetailRequest::class.java)
            val data = response.body!!.detailData

            logger?.info("name: ${data.name} regions: ${data.numRegions}")
            return buildCountryObj(data)
        } catch (error: HttpClientErrorException) {
            logger?.error("Error requesting $countryCode. Msg: ${error.message}")
        }
        return null
    }
}
