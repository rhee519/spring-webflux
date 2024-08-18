package com.example.headoffice

import Book
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import java.time.LocalDateTime

@SpringBootApplication
class HeadOfficeApplication {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun restTemplateBuilder() = RestTemplateBuilder()

    @Bean
    fun run(): CommandLineRunner {
        return CommandLineRunner {
            log.info("[${LocalDateTime.now()}] Start fetching books from branch office")
            (1..5).forEach { bookId ->
                val book = getBookFromBranchOffice(bookId.toLong())
                log.info("[${LocalDateTime.now()}] Book: $book")
            }
        }
    }

    private fun getBookFromBranchOffice(bookId: Long): Book? {
        val restTemplate = RestTemplateBuilder().build()
        val response = restTemplate.getForEntity("http://localhost:7070/v1/books/${bookId}", Book::class.java)
        return response.body
    }
}

fun main(args: Array<String>) {
    runApplication<HeadOfficeApplication>(*args)
}
