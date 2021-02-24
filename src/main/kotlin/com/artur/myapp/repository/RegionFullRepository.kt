package com.artur.myapp.repository

import com.artur.myapp.data.region.CountryRegion
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionFullRepository: MongoRepository<CountryRegion, String> {
}
