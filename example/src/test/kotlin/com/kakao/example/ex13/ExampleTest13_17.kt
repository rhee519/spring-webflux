package com.kakao.example.ex13

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import kotlin.test.Test

class ExampleTest13_17 {
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
            .expectRecordedMatches { countries -> countries.stream().allMatch { Character.isUpperCase(it[0]) } }
            .expectComplete()
            .verify()
    }
}