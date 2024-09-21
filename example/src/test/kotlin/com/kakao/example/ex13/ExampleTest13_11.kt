package com.kakao.example.ex13

import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.test.StepVerifier
import kotlin.test.Test

class ExampleTest13_11 {
    @Test
    fun backpressureTest() {
        val numbers = Flux.create({ sink ->
            IntRange(1, 100).forEach { sink.next(it) }
            sink.complete()
        }, FluxSink.OverflowStrategy.ERROR)

        StepVerifier
            .create(numbers, 1) // backpressure: 1
            .thenConsumeWhile { it >= 1 }
//            .verifyComplete() // java.lang.AssertionError: expectation "expectComplete" failed (expected: onComplete(); actual: onError(reactor.core.Exceptions$OverflowException: The receiver is overrun by more signals than expected (bounded queue...)))
            .verifyError()
    }
}