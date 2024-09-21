package com.kakao.example.ex11

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.util.context.Context

@Slf4j
class Example11_8 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val HEADER_AUTH_TOKEN = "X-AUTH-TOKEN"

        data class Book(
            val isbn: String,
            val bookName: String,
            val author: String
        )

        /**
         * Context는 인증 정보와 같이 직교성(독립성)을 가지는 데이터를 전파할 때 유용함
         * output:
         * [2024-09-21T17:10:09.326+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T17:10:09.371+09:00][ INFO] [main] # onNext: POST the book(Kotlin in Action, Dmitry Jemerov, Svetlana Isakova) with the auth token(qeirl23r49dja)
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val mono = postBook(
                Mono.just(Book("9788966262281", "Kotlin in Action", "Dmitry Jemerov, Svetlana Isakova"))
            ).contextWrite(Context.of(HEADER_AUTH_TOKEN, "qeirl23r49dja"))

            mono.subscribe { logger.info("# onNext: $it") }
            Thread.sleep(500)
        }

        private fun postBook(book: Mono<Book>): Mono<String> {
            return Mono.zip(book, Mono.deferContextual { ctx ->
                val authToken = ctx.get<String>(HEADER_AUTH_TOKEN)
                Mono.just(authToken)
            })
                .flatMap {
                    val postedBook = it.t1
                    val authToken = it.t2
                    val response =
                        "POST the book(${postedBook.bookName}, ${postedBook.author}) with the auth token($authToken)"
                    Mono.just(response)
                }
        }
    }
}