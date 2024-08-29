package com.kakao.example

import reactor.core.publisher.Flux

fun main(args: Array<String>) {
    Flux.concat(
        Flux.just("Mercury", "Venus", "Earth"),
        Flux.just("Mars", "Jupiter", "Saturn"),
        Flux.just("Uranus", "Neptune")
    )
        .collectList()
        .subscribe { println(it) }
}
