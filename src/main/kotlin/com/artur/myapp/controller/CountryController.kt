package com.artur.myapp.controller


import com.artur.myapp.data.country.Country
import com.artur.myapp.data.country.CountryFull
import com.artur.myapp.service.CountryService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/country")
class CountryController(private val countryService: CountryService) {

    @GetMapping()
    fun getAllCountries(
        @RequestParam("sort") sorted: String?,
        @RequestParam("page") paramPage: Int?,
        @RequestParam("items") offSet: Int?
    ): ResponseEntity<Page<Country>> {
        val page = paramPage ?: 0
        val numItems = offSet ?: 15

        val countriesList = if (sorted != null) {
            countryService.getAllPagedAndSorted(sorted, page, numItems)
        } else {
            countryService.getAllPaged(page, numItems)
        }

        return ResponseEntity.ok(countriesList)
    }

    @GetMapping("/all")
    fun getAllIdName(): ResponseEntity<List<Country>> {
        return ResponseEntity.ok(countryService.getAll())
    }

    //TODO: usar o serviceo no lugar do repositorio;
    // Criar rota get all country/code apenas. Sem detalhes;
    // Assim, a busca funciona.
    // Usar os detalhes parciais dos countries na tela de paginacao.
    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String): ResponseEntity<CountryFull> {
        val country = countryService.getCountryDetails(id.toUpperCase())
        if (country.isEmpty) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(country.get())
    }

}
