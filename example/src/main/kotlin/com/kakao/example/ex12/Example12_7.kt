package com.kakao.example.ex12

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import java.util.logging.Level

class Example12_7 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private val fruits = mapOf(
            "banana" to "바나나",
            "apple" to "사과",
            "grape" to "포도",
            "kiwi" to "키위",
        )

        /**
         * ```
         * Debugging Example (log())
         *
         * output:
         * [2024-09-21T21:56:39.951+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T21:56:39.961+09:00][DEBUG] [main] | onSubscribe([Fuseable] FluxMapFuseable.MapFuseableSubscriber)
         * [2024-09-21T21:56:39.962+09:00][DEBUG] [main] | request(unbounded)
         * [2024-09-21T21:56:39.964+09:00][DEBUG] [main] | onNext(banana)
         * [2024-09-21T21:56:39.964+09:00][ INFO] [main] 바나나
         * [2024-09-21T21:56:39.965+09:00][DEBUG] [main] | onNext(apple)
         * [2024-09-21T21:56:39.965+09:00][ INFO] [main] 사과
         * [2024-09-21T21:56:39.965+09:00][DEBUG] [main] | onNext(grape)
         * [2024-09-21T21:56:39.965+09:00][ INFO] [main] 포도
         * [2024-09-21T21:56:39.965+09:00][DEBUG] [main] | onNext(melon)
         * [2024-09-21T21:56:39.966+09:00][DEBUG] [main] | cancel()
         * [2024-09-21T21:56:39.967+09:00][ERROR] [main] # onError:
         * java.lang.NullPointerException: The mapper [com.kakao.example.ex12.Example12_7$Companion$$Lambda$131/0x000000700110a108] returned a null value.
         * 	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onNext(FluxMapFuseable.java:115)
         * 	...
         * 	```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(listOf("BANANAS", "APPLES", "GRAPES", "MELONS"))
                .map(String::lowercase)
                .map { it.take(it.length - 1) }
//                .log() // INFO
                .log("take()", Level.FINE) // DEBUG
                .map(fruits::get)
                .subscribe(log::info) { error -> log.error("# onError: ", error) }
        }
    }
}