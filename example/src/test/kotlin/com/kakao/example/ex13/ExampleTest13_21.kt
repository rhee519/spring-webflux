package com.kakao.example.ex13

import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.test.publisher.PublisherProbe
import kotlin.test.Test

class ExampleTest13_21 {
    companion object {
        private fun processTask(main: Mono<String>, standby: Mono<String>): Mono<String> {
            return main.switchIfEmpty(standby)
        }

        private fun supplyMainPower() = Mono.empty<String>()

        private fun supplyStandbyPower() = Mono.just("# supply Standby Power")
    }

    /**
     * ```
     * PublisherProbe example
     * Publisher를 PublisherProbe로 감싸 Publisher의 동작을 관찰할 수 있음
     * 특정 조건에 따라 Sequence가 분기되는 상황에서 실제로 어느 Publisher가 동작하는지 확인하는 용도로 사용할 수 있음
     * ```
     */
    @Test
    fun misbehavingTestPublisherTest() {
        val probe = PublisherProbe.of(supplyStandbyPower()) // stanby Publisher를 관찰하기 위한 probe 객체

        StepVerifier
            .create(processTask(supplyMainPower(), probe.mono()))
            .expectNextCount(1)
            .verifyComplete()

        // main 대신 standby가 동작했는지 확인
        probe.assertWasSubscribed()
        probe.assertWasRequested()
        probe.assertWasNotCancelled()
    }
}