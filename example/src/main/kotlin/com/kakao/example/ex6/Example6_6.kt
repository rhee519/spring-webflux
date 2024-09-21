package com.kakao.example.ex6

import reactor.core.publisher.Mono

fun main(args: Array<String>) {
    val flux = Mono.justOrEmpty("Steve")
        .concatWith(Mono.justOrEmpty("Tony"))
    flux.subscribe { println(it) }
}
