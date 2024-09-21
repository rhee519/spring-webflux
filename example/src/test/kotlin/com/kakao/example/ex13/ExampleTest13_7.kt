package com.kakao.example.ex13

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import java.time.Duration
import kotlin.test.Test

class ExampleTest13_7 {
    @Test
    fun withVirtualTimeTest() {
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
            .withVirtualTime {
                Flux.interval(Duration.ofHours(1)) // 1시간 간격으로 데이터를 emit하는 Flux 객체
                    .take(1)
                    .flatMap { covid19Count }
            }
            .expectSubscription()
            .then { VirtualTimeScheduler.get().advanceTimeBy(Duration.ofHours(1)) } // 가상 스케줄러를 생성하여 1시간 뒤로 시간을 이동
            .expectNextCount(11)
            .verifyComplete()
    }
}