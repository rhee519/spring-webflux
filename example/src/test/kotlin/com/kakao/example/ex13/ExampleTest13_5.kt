package com.kakao.example.ex13

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.test.StepVerifierOptions
import kotlin.test.Test

class ExampleTest13_5 {
    @Test
    fun expectNextCountTest() {
        val source = Flux.range(0, 1000)
        StepVerifier
            .create(
                source.take(500),
                StepVerifierOptions.create().scenarioName("Verify from 0 to 499")
            )
            .expectSubscription()
            .expectNext(0)
            .expectNextCount(498)
//            .expectNext(500) // [Verify from 0 to 499] expectation "expectNext(500)" failed (expected value: 500; actual value: 499)
            .expectNext(499)
            .verifyComplete()
    }
}