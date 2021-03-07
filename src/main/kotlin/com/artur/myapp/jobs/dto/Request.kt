package com.artur.myapp.jobs.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Metadata(
    @JsonProperty("currentOffset")val currentOffset: Int,
    @JsonProperty("totalCount")val totalCount: Int,
)

data class Link(
    @JsonProperty("rel")val rel: String,
    @JsonProperty("href")val href: String,
)
