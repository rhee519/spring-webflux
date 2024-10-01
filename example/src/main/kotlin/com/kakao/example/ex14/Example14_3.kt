package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_3 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-01T20:48:45.126+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-01T20:48:45.161+09:00][ INFO] [main] data: BTC
         * [2024-10-01T20:48:45.161+09:00][ INFO] [main] data: ETH
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromStream(SampleData.coinNames.stream())
                .filter { it == "BTC" || it == "ETH" }
                .subscribe { log.info("data: $it") }
        }
    }
}