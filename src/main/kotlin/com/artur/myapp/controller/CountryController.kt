package com.artur.myapp.controller


import com.artur.myapp.data.country.Country
import com.artur.myapp.data.country.CountryFull
import com.artur.myapp.repository.CountryFullRepository
import com.artur.myapp.repository.CountryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/country")
class CountryController(
    private val countryRepository: CountryRepository,
    private val countryFullRepository: CountryFullRepository) {

    @GetMapping()
    fun getAllCountries(
        @RequestParam("page") paramPage: Int?,
        @RequestParam("items") offSet: Int?): ResponseEntity<Page<Country>> {
        val page = paramPage ?: 0
        val numItems = offSet ?: 5
        val countriesPaged = countryRepository.findAll(PageRequest.of(page, numItems))
        return ResponseEntity.ok(countriesPaged)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String): ResponseEntity<CountryFull> {
        val country = countryFullRepository.findById(id.toUpperCase())
        if (country.isEmpty) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(country.get())
    }

}
