package com.kakao.example.ex5

import reactor.core.publisher.Flux


fun main(args: Array<String>) {
    val sequence = Flux.just("Hello", "Reactor")
    sequence
        .map { it.lowercase() }
        .subscribe(::println)
}
