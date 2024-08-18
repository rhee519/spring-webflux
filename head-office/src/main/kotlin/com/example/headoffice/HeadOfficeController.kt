package com.example.headoffice

import Book
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@Slf4j
@RestController
class HeadOfficeController(
    restTemplateBuilder: RestTemplateBuilder
) {
    private val restTemplate = restTemplateBuilder.build()
    private val branchOfficeBaseUri = UriComponentsBuilder.newInstance()
        .scheme("http")
        .host("localhost")
        .port(7070)
        .build()
        .encode()
        .toUri()

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/books/{bookId}")
    fun getBook(@PathVariable bookId: Long): ResponseEntity<Book?> {
        val getBookUri = UriComponentsBuilder.fromUri(branchOfficeBaseUri)
            .path("/v1/books/{bookId}")
            .buildAndExpand(bookId)
            .encode()
            .toUri()
        val response = restTemplate.getForEntity(getBookUri, Book::class.java)
        return ResponseEntity.ok(response.body)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/health")
    fun health(): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }
}