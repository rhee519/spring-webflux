package com.kakao.example.ex13

import org.assertj.core.api.Assertions.assertThat
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import kotlin.test.Test

class ExampleTest13_16 {
    @Test
    fun recordTest() {
        StepVerifier
            .create(
                Flux.just("korea", "japan", "china")
                    .map { it.take(1).uppercase() + it.substring(1) }
            )
            .expectSubscription()
            .recordWith { mutableListOf() } // emit되는 데이터를 mutable collection에 record
            .thenConsumeWhile { it.isNotEmpty() } // record collection으로부터 데이터를 consume
            .consumeRecordedWith {
                assertThat(it.stream().allMatch { country ->
                    Character.isUpperCase(country[0])
                })
            }
            .expectComplete()
            .verify()
    }
}