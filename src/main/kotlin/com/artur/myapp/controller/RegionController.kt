package com.artur.myapp.controller

import com.artur.myapp.data.region.CountryRegion
import com.artur.myapp.repository.RegionFullRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/region")
class RegionController(val regionFullRepository: RegionFullRepository) {

    @GetMapping("/{country}")
    fun getRegions(@PathVariable("country") countryCode: String): ResponseEntity<Optional<CountryRegion>> {
        return ResponseEntity.ok(regionFullRepository.findById(countryCode.toUpperCase()));
    }
}
