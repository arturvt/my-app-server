package com.artur.myapp.service

import com.artur.myapp.data.country.Country
import com.artur.myapp.data.country.CountryFull
import com.artur.myapp.repository.country.CountryFullRepository
import com.artur.myapp.repository.country.CountryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*


@Service
class CountryService(
    private val countryRepository: CountryRepository,
    private val countryFullRepository: CountryFullRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(CountryService::class.java)

    fun getAllPaged(page: Int, numItems: Int): Page<Country> {
        return countryRepository.findAll(PageRequest.of(page, numItems))
    }

    fun getAllPagedAndSorted(sorted: String, page: Int, numItems: Int): Page<Country> {
        val sortDirection = if (sorted.toLowerCase().contains("asc")) {
            Sort.Direction.ASC
        } else {
            Sort.Direction.DESC
        }
        val sort = Sort.by(sortDirection, "name")
        return countryRepository.findAll(PageRequest.of(page, numItems, sort))
    }

    fun getCountryDetails(code: String): Optional<CountryFull> {
        return countryFullRepository.findById(code)
    }

    fun getAll(): List<Country> {
        return countryRepository.findAll()
    }

}
