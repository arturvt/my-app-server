package com.artur.myapp.controller

import com.artur.myapp.service.CountryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/job")
class FetcherController(private val service: CountryService) {

    @PostMapping
    fun startJob(@RequestBody countryKeys: List<String>): ResponseEntity<String> {
        val response = service.jobSearchCountries(countryKeys)
        return ResponseEntity.ok().body(response)
    }
}
