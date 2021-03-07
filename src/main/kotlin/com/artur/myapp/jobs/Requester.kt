package com.artur.myapp.jobs

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun doRequest(url: String): HttpResponse<String>? {
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .setHeader("Content-Type", "application/json")
        .setHeader("x-rapidapi-hos", "wft-geo-db.p.rapidapi.com")
        .setHeader("x-rapidapi-key", "4afD687vWcZfy5rQUP9BTvA255dNQ4Oi")
        .build()

    return client.send(request, HttpResponse.BodyHandlers.ofString())
}
