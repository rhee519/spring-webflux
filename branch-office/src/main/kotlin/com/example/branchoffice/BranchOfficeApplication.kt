package com.example.branchoffice

import Book
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BranchOfficeApplication {
    @Bean
    fun bookMap() = mapOf(
        1L to Book(1L, "Kotlin In Action"),
        2L to Book(2L, "Spring Boot In Action"),
        3L to Book(3L, "Effective Java"),
        4L to Book(4L, "Clean Code"),
        5L to Book(5L, "Design Patterns")
    )
}

fun main(args: Array<String>) {
    runApplication<BranchOfficeApplication>(*args)
}
