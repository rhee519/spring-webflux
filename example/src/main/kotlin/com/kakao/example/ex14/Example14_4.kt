package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_4 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-01T20:49:43.841+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-01T20:49:43.846+09:00][ INFO] [main] data: 5
         * [2024-10-01T20:49:43.847+09:00][ INFO] [main] data: 6
         * [2024-10-01T20:49:43.847+09:00][ INFO] [main] data: 7
         * [2024-10-01T20:49:43.847+09:00][ INFO] [main] data: 8
         * [2024-10-01T20:49:43.847+09:00][ INFO] [main] data: 9
         * [2024-10-01T20:49:43.847+09:00][ INFO] [main] data: 10
         * [2024-10-01T20:49:43.847+09:00][ INFO] [main] data: 11
         * [2024-10-01T20:49:43.847+09:00][ INFO] [main] data: 12
         * [2024-10-01T20:49:43.847+09:00][ INFO] [main] data: 13
         * [2024-10-01T20:49:43.847+09:00][ INFO] [main] data: 14
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.range(5, 10)
                .subscribe { log.info("data: $it") }
        }
    }
}