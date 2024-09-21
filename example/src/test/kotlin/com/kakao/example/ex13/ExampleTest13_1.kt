package com.kakao.example.ex13

import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import kotlin.test.Test

class ExampleTest13_1 {
    @Test
    fun createMonoTest() {
        StepVerifier
            .create(Mono.just("Hello Reactor")) // 테스트 대상 Sequence 생성
            .expectNext("Hello Reactor") // emit된 데이터 기댓값 평가
            .expectComplete() // onComplete Signal 기댓값 평가
            .verify() // 테스트 실행
    }
}