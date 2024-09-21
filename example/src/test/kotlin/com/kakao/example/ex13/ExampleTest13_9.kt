package com.kakao.example.ex13

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration
import kotlin.test.Test

class ExampleTest13_9 {
    @Test
    fun expectNoEventTest() {
        val voteCount = Flux.just(
            "중구" to 15400,
            "서초구" to 20200,
            "강서구" to 32040,
            "강동구" to 1455506,
            "서대문구" to 35650,
        )

        StepVerifier
            .withVirtualTime {
                Flux.interval(Duration.ofMinutes(1)) // 1분 간격으로 데이터를 emit하는 Flux 객체
                    .zipWith(voteCount) { _, count -> count }
            }
            .expectSubscription()
            .expectNoEvent(Duration.ofMinutes(1)) // 1분 동안 이벤트가 없는지 확인 & virtual time을 1분 이동
            .expectNoEvent(Duration.ofMinutes(1))
            .expectNoEvent(Duration.ofMinutes(1))
            .expectNoEvent(Duration.ofMinutes(1))
            .expectNoEvent(Duration.ofMinutes(1))
            .expectNextCount(5)
            .expectComplete()
            .verify()
    }
}