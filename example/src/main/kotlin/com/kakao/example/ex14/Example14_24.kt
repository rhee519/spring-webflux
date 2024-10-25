package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_24 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * takeUntil(): predicate이 true가 될 때까지 emit
         *
         * output:
         * [2024-10-25T14:46:04.413+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T14:46:04.447+09:00][ INFO] [main] 2020: 22439002
         * [2024-10-25T14:46:04.447+09:00][ INFO] [main] 2021: 63364000
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(SampleData.btcTopPricesPerYear)
                .takeUntil { it.t2 > 20_000_000 }
                .subscribe { log.info("${it.t1}: ${it.t2}") }
        }
    }
}
