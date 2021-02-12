package com.artur.myapp.jobs.dto

import com.artur.myapp.data.region.Region
import com.fasterxml.jackson.annotation.JsonProperty

data class RegionRequest(
    @JsonProperty("metadata")val metadata: Metadata,
    @JsonProperty("links")val links: List<Link>,
    @JsonProperty("data")val data: List<Region>)

