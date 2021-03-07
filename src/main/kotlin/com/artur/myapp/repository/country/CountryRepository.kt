package com.artur.myapp.repository.country

import com.artur.myapp.data.country.Country
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : MongoRepository<Country, String> {
}
