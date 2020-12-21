package com.artur.myapp.controller


import com.artur.myapp.data.Country
import com.artur.myapp.data.CountryRequest
import com.artur.myapp.repository.CountryRepository
import com.artur.myapp.service.CountryService
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/country")
class CountryController(private val countryRepository: CountryRepository, private val service: CountryService) {

    @GetMapping
    fun getAllCountries(): ResponseEntity<List<Country>> {
        val countries = countryRepository.findAll()
        return ResponseEntity.ok(countries)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String): ResponseEntity<Country> {
        val country = countryRepository.findById(id.toUpperCase())
        if (country.isEmpty) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(country.get())
    }

    @PostMapping
    fun saveCountry(@RequestBody countryRequest: CountryRequest): ResponseEntity<Country> {
        val country = countryRequest.data
        val saved = countryRepository.save(Country(
                id = country.code,
                code =  country.code,
                name = country.name,
                currencyCodes = country.currencyCodes,
                flagImageUri = country.flagImageUri,
                numRegions = country.numRegions,
                wikiData = country.wikiData,
                region = listOf()
        ))
        return ResponseEntity.ok(saved)
    }

}
