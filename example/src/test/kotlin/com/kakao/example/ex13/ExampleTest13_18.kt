package com.kakao.example.ex13

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.test.publisher.TestPublisher
import kotlin.test.Test

class ExampleTest13_18 {
    /**
     * ```
     * 정상 동작(well-behaved)하는 TestPublisher를 사용한 테스트
     * ```
     */
    @Test
    fun wellBehavedTestPublisherTest() {
        val source = TestPublisher.create<Int>()

        StepVerifier
            .create(
                source.flux()
                    .zipWith(Flux.just(2, 2, 2, 2, 0)) { a, b -> a / b }
            )
            .expectSubscription()
            .then { source.emit(2, 4, 6, 8, 10) }
            .expectNext(1, 2, 3, 4)
            .expectError() // 10 / 0
            .verify()
    }
}