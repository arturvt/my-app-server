package com.artur.myapp.service

import com.artur.myapp.data.Country
import com.artur.myapp.data.CountryDataRequest
import com.artur.myapp.data.CountryRequest
import com.artur.myapp.data.RegionRequest
import com.artur.myapp.enum.RequestType
import com.artur.myapp.repository.CountryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.util.concurrent.atomic.AtomicInteger


@Service
class CountryService(private val countryRepository: CountryRepository,
                     private val restTemplateBuilder: RestTemplateBuilder) {

    private lateinit var  httpEntity: HttpEntity<String>
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

    fun jobSearchCountries(listCodes: List<String>, type: RequestType): String {
        if (listCodes.isNotEmpty()) {
            if(runningJobs.get() == 0) {
                Thread {
                    runningJobs.set(listCodes.size)
                    logger?.info("Starting requests for  ${runningJobs.get() + 1}")
                    listCodes.forEach {
                        val countryIdExists = keyExists(it)
                        if (!countryIdExists && type == RequestType.COUNTRY) {
                            doCountryRequest(it)
                            Thread.sleep(2500)
                        } else if (countryIdExists && type == RequestType.REGION) {
                            doRegionRequest(it.toUpperCase())
                            Thread.sleep(2500)
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

    private fun keyExists(key: String): Boolean {
        return countryRepository.existsById(key.toUpperCase())
    }

    private fun buildCountryObj(country: CountryDataRequest): Country {
        return Country(
                id = country.code,
                code =  country.code,
                name = country.name,
                currencyCodes = country.currencyCodes,
                flagImageUri = country.flagImageUri,
                numRegions = country.numRegions,
                wikiData = country.wikiData,
                region = listOf()
        )
    }

    private fun doRegionRequest(countryCode: String) {
        logger?.info("Region request for $countryCode")
        val url = "$countriesUrl/${countryCode}/regions"
        try {
            val response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, RegionRequest::class.java)
            val data = response.body!!.data

            logger?.info("Country $countryCode with ${data.size} and 1st is ${data[0].name}")
            val country = countryRepository.findById(countryCode).get()
            country.region = data
            val saved = countryRepository.save(country)
            logger?.info("persisted:  ${saved.id}")
        } catch (error: HttpClientErrorException) {
            logger?.error("Error requesting $countryCode. Msg: ${error.message}")
        } catch (error: HttpMessageNotReadableException) {
            logger?.error("Error requesting $countryCode. Msg: ${error.message}")
        }
    }

    private fun doCountryRequest(countryCode: String) {
        logger?.info("Doing request for $countryCode")
        val url = "$countriesUrl/${countryCode}"

        try {
            val response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, CountryRequest::class.java)
            val data = response.body!!.data

            logger?.info("name: ${data.name} regions: ${data.numRegions}")
            val saved = countryRepository.save(buildCountryObj(data))
            logger?.info("persisted:  ${saved.id}")
        } catch (error: HttpClientErrorException) {
            logger?.error("Error requesting $countryCode. Msg: ${error.message}")
        }
    }
}
