package com.kakao.example.ex6

import reactor.core.publisher.Flux

fun main(args: Array<String>) {
    Flux.fromIterable(listOf(3, 6, 7, 9))
        .filter { it > 6 }
        .map { it * 2 }
        .subscribe(::println)
}
