package com.artur.myapp.repository

import com.artur.myapp.data.Country
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface CountryRepository: MongoRepository<Country, String> {
    fun findById(id: ObjectId): Country
}
