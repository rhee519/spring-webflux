package com.kakao.example

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.util.*

fun main(args: Array<String>) {
    val worldTimeUri = UriComponentsBuilder.newInstance()
        .scheme("http")
        .host("worldtimeapi.org")
        .path("/api/timezone/Asia/Seoul")
        .build()
        .encode()
        .toUri()
    val restTemplate = RestTemplate()
    val headers = HttpHeaders()
    headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON)

    val objectMapper = ObjectMapper()

    Mono.just(
        restTemplate.exchange(
            worldTimeUri,
            HttpMethod.GET,
            HttpEntity<String>(headers),
            String::class.java
        )
    ).map {
        val dateTime = objectMapper.readTree(it.body).get("datetime").asText()
        dateTime
    }
        .subscribe(
            { println("# emitted: $it") },
            { error -> println("# onError signal: ${error.message}") },
            { println("# onComplete signal") }
        )
}
