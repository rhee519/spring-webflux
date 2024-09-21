package com.kakao.example.ex13

import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*
import kotlin.test.Test

class ExampleTest13_14 {
    @Test
    fun contextTest() {
        StepVerifier
            .create(
                Mono.just("hello")
                    .zipWith(Mono.deferContextual { ctx ->
                        val secretKey = ctx.get<String>("secretKey")
                        Mono.just(secretKey)
                    })
                    .filter { it.t1 == String(Base64.getDecoder().decode(it.t2)) }
                    .transformDeferredContextual { mono, ctx ->
                        val secretMessage = ctx.get<String>("secretMessage")
                        mono.map { secretMessage }
                    }
                    .contextWrite { it.put("secretMessage", "Hello, Reactor") }
                    .contextWrite { it.put("secretKey", Base64.getEncoder().encodeToString("hello".toByteArray())) }
            )
            .expectSubscription()
            .expectAccessibleContext()
            .hasKey("secretKey")
            .hasKey("secretMessage")
            .then()
            .expectNext("Hello, Reactor")
            .expectComplete()
            .verify()
    }
}