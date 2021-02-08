package com.artur.myapp.repository

import com.artur.myapp.data.country.Country
import com.artur.myapp.data.country.CountryId
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository: MongoRepository<Country, String> {
    fun findById(id: ObjectId): Country
}
