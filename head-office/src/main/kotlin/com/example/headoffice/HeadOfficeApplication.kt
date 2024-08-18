package com.example.headoffice

import Book
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@SpringBootApplication
class HeadOfficeApplication {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun run(): CommandLineRunner {
        return CommandLineRunner {
            log.info("[${LocalDateTime.now()}] Start fetching books from branch office")
            (1..5).map { it.toLong() }.forEach { bookId ->
                getBookFromBranchOffice(bookId).subscribe { book ->
                    log.info("[${LocalDateTime.now()}] Received book: $book")
                }
            }
        }
    }

    private fun getBookFromBranchOffice(bookId: Long): Mono<Book?> {
        val getBookUri = "http://localhost:7070/v1/books/${bookId}"
        return WebClient.create()
            .get()
            .uri(getBookUri)
            .retrieve()
            .bodyToMono(Book::class.java)
    }
}

fun main(args: Array<String>) {
//    System.setProperty("reactor.netty.ioWorkerCount", "1")
    runApplication<HeadOfficeApplication>(*args)
}
