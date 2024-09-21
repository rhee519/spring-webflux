package com.kakao.example.ex13

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import kotlin.test.Test

class ExampleTest13_3 {
    @Test
    fun createFluxTest() {
        StepVerifier
            .create(Flux.just("Hello", "Reactor"))
            .expectSubscription()
            .`as`("# expect subscription")
//            .expectNext("Hi")
//            .`as`("# expect Hi") // java.lang.AssertionError: expectation "# expect Hi" failed (expected value: Hi; actual value: Hello)
            .expectNext("Hello")
            .`as`("# expect Hello")
            .expectNext("Reactor")
            .`as`("# expect Reactor")
            .verifyComplete()
    }
}