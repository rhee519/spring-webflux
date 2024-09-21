package com.kakao.example.ex13

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import kotlin.test.Test

class ExampleTest13_4 {
    @Test
    fun expectErrorTest() {
        val source = Flux.just(2, 4, 6, 8, 10)
        StepVerifier
            .create(source.zipWith(Flux.just(2, 2, 2, 2, 0)) { a, b -> a / b })
            .expectSubscription()
//            .expectNext(1)
//            .expectNext(2)
//            .expectNext(3)
//            .expectNext(4)
            .expectNext(1, 2, 3, 4)
            .expectError()
            .verify()
    }
}