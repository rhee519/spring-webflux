package com.kakao.example

import reactor.core.publisher.Mono


fun main(args: Array<String>) {
    Mono.just("Hello, Reactor")
        .subscribe(::println)
}
