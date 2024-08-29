package com.kakao.example

import reactor.core.publisher.Mono

fun main(args: Array<String>) {
    Mono.empty<Any>()
        .subscribe(
            { println("# emitted($it) onNext signal") },
            { error -> println("# onError signal: ${error.message}") },
            { println("# onComplete signal") }
        )
}
