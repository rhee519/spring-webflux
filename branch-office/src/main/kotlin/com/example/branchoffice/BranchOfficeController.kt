package com.example.branchoffice

import Book
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class BranchOfficeController(
    private val bookMap: Map<Long, Book>,
) {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/books/{bookId}")
    fun getBook(@PathVariable bookId: Long): ResponseEntity<Book?> {
        Thread.sleep(5000)
        return ResponseEntity.ok(bookMap[bookId])
    }
}