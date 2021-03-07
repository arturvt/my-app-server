package com.artur.myapp.jobs.dto

import com.artur.myapp.data.country.CountryFull
import com.fasterxml.jackson.annotation.JsonProperty

data class CountryDetailRequest(
    @JsonProperty("data") val data: CountryFull
)
