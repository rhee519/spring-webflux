package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_25_Flux_takeWhile {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * takeWhile(): predicate이 true인 동안에만 emit
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
                .takeWhile { it.t2 < 20_000_000 }
                .subscribe { log.info("${it.t1}: ${it.t2}") }
        }
    }
}
