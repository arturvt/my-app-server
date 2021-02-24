package com.artur.myapp.repository

import com.artur.myapp.data.country.Country
import com.artur.myapp.data.country.CountryFull
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository: MongoRepository<Country, String> {
    fun findById(id: ObjectId): Country
}
