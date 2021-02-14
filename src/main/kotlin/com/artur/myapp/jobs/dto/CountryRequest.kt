package com.artur.myapp.jobs.dto

import com.artur.myapp.data.country.Country
import com.fasterxml.jackson.annotation.JsonProperty

data class CountryRequest(
        @JsonProperty("metadata")val metadata: Metadata,
        @JsonProperty("links")val links: List<Link>,
        @JsonProperty("data")val data: List<Country>)

