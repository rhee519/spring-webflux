package com.kakao.example

import reactor.core.publisher.Flux

fun main(args: Array<String>) {
    Flux.just(6, 9, 13)
        .map { it % 2 }
        .subscribe(::println)
}
