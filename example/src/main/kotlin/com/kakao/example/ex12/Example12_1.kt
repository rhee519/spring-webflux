package com.kakao.example.ex12

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Hooks
import reactor.core.scheduler.Schedulers

class Example12_1 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private val fruits = mapOf(
            "banana" to "바나나",
            "apple" to "사과",
            "grape" to "포도",
            "kiwi" to "키위",
        )

        /**
         * ```
         * Debugging Example (Hooks.onOperatorDebug())
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Hooks.onOperatorDebug() // stacktrace를 캡쳐하여 에러가 발생한 Assembly를 찾아줌

            Flux
                .fromIterable(
                    listOf(
                        "BANANA",
                        "APPLE",
                        "GRAPE",
                        "KIWI",
                        "MELON" // java.lang.NullPointerException
                    )
                )
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .map(String::lowercase)
                .map(fruits::get)
                .map { "맛있는 $it" }
                .subscribe(
                    { logger.info("# onNext: $it") },
                    { error -> logger.error("# onError: ", error) },
                )

            Thread.sleep(100)
        }
    }
}