package com.artur.myapp.service

import com.artur.myapp.repository.CountryRepository
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service


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

}
