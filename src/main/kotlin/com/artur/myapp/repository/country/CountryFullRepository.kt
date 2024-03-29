package com.artur.myapp.repository.country

import com.artur.myapp.data.country.CountryFull
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryFullRepository: MongoRepository<CountryFull, String> {
}
