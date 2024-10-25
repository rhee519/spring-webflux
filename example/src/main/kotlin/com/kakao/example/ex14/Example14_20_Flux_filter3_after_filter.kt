package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_20_Flux_filter3_after_filter {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-25T14:38:20.677+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T14:38:20.711+09:00][ INFO] [main] 2021: 63364000
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(SampleData.btcTopPricesPerYear)
                .filter { it.t2 >= 20_000_000 }
                .skip(2)
                .subscribe { log.info("${it.t1}: ${it.t2}") }
        }
    }
}
