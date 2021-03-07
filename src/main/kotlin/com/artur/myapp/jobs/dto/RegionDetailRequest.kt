package com.artur.myapp.jobs.dto

import com.artur.myapp.data.region.RegionFull
import com.fasterxml.jackson.annotation.JsonProperty

data class RegionDetailRequest(
    @JsonProperty("data") val data: RegionFull
)
