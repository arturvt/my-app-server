package com.artur.myapp.controller

import com.artur.myapp.enum.RequestType
import com.artur.myapp.service.CountryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/job")
class FetcherController(private val service: CountryService) {

    @PostMapping("/country")
    fun startCountryJob(@RequestBody countryKeys: List<String>): ResponseEntity<String> {
        val response = service.jobSearchCountries(countryKeys, RequestType.COUNTRY)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/region")
    fun startRegionJob(@RequestBody countryKeys: List<String>): ResponseEntity<String> {
        val response = service.jobSearchCountries(countryKeys, RequestType.REGION)
        return ResponseEntity.ok().body(response)
    }
}
