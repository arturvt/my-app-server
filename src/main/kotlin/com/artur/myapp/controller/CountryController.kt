package com.artur.myapp.controller


import com.artur.myapp.data.country.CountryFull
import com.artur.myapp.repository.CountryFullRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/country")
class CountryController(private val countryRepository: CountryFullRepository) {

    @GetMapping
    fun getAllCountries(): ResponseEntity<List<CountryFull>> {
        val countries = countryRepository.findAll()
        return ResponseEntity.ok(countries)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String): ResponseEntity<CountryFull> {
        val country = countryRepository.findById(id.toUpperCase())
        if (country.isEmpty) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(country.get())
    }

}
