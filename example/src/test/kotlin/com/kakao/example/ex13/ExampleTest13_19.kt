package com.kakao.example.ex13

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.test.publisher.TestPublisher
import kotlin.test.Ignore

class ExampleTest13_19 {
    /**
     * ```
     * 오동작(misbehaving)하는 TestPublisher를 사용한 테스트
     * reactive streams 사양에 위반되는 데이터를 emit하게 만들 수 있음
     * ```
     */
    @Ignore
    fun misbehavingTestPublisherTest() {
//        val source = TestPublisher.create<Int>() // onNext Signal 전송 전에 validation을 수행하여 emit할 데이터가 null이면 미리 NPE 발생
        val source =
            TestPublisher.createNoncompliant<Int>(TestPublisher.Violation.ALLOW_NULL) // validation 하지 않고 null 데이터를 emit

        StepVerifier
            .create(
                source.flux()
                    .zipWith(Flux.just(2, 2, 2, 2, 0)) { a, b -> a / b }
            )
            .expectSubscription()
            .then {
                listOf(2, 4, 6, 8, null).forEach { source.next(it) }
                source.complete()
            }
            .expectNext(1, 2, 3, 4)
            .expectComplete()
            .verify()
    }
}