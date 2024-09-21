package com.kakao.example.ex6

import reactor.core.publisher.Mono


fun main(args: Array<String>) {
    Mono.just("Hello, Reactor")
        .subscribe(::println)
}
