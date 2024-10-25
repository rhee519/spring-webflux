package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_16_Flux_filter2 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-25T14:21:29.979+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T14:21:30.011+09:00][ INFO] [main] # 2017: 22483583
         * [2024-10-25T14:21:30.011+09:00][ INFO] [main] # 2020: 22439002
         * [2024-10-25T14:21:30.011+09:00][ INFO] [main] # 2021: 63364000
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(SampleData.btcTopPricesPerYear)
                .filter { it.t2 > 20_000_000 }
                .subscribe { log.info("# ${it.t1}: ${it.t2}") }
        }
    }
}
