package com.kakao.example.ex13

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration
import kotlin.test.Test

class ExampleTest13_8 {
    @Test
    fun withActualTimeTest() {
        val covid19Count = Flux.just(
            "서울" to 10,
            "경기도" to 5,
            "강원도" to 3,
            "충청도" to 6,
            "경상도" to 5,
            "전라도" to 8,
            "인천" to 2,
            "대전" to 1,
            "대구" to 2,
            "부산" to 3,
            "제주도" to 0
        )

        StepVerifier
            .create(
                Flux.interval(Duration.ofSeconds(2)) // 2초 간격으로 데이터를 emit하는 Flux 객체
                    .take(1)
                    .flatMap { covid19Count }
            )
            .expectSubscription()
            .expectNextCount(11)
            .expectComplete()
//            .verify(Duration.ofSeconds(1)) // java.lang.AssertionError: VerifySubscriber timed out on reactor.core.publisher.FluxFlatMap$FlatMapMain@2756c0a7
            .verify(Duration.ofSeconds(3))
    }
}