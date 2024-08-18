package com.example.headoffice

import Book
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@RestController
class HeadOfficeController {
    private val branchOfficeBaseUri = UriComponentsBuilder.newInstance()
        .scheme("http")
        .host("localhost")
        .port(7070)
        .build()
        .encode()
        .toUri()

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/books/{bookId}")
    fun getBook(@PathVariable bookId: Long): Mono<Book?> {
        val getBookUri = UriComponentsBuilder.fromUri(branchOfficeBaseUri)
            .path("/v1/books/{bookId}")
            .buildAndExpand(bookId)
            .encode()
            .toUri()
        return WebClient.create()
            .get()
            .uri(getBookUri)
            .retrieve()
            .bodyToMono(Book::class.java)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/health")
    fun health(): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }
}