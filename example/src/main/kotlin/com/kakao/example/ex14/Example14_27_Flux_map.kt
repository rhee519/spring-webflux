package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_27_Flux_map {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-25T15:02:41.307+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T15:02:41.317+09:00][ INFO] [main] # onNext: 1-Rectangle
         * [2024-10-25T15:02:41.317+09:00][ INFO] [main] # onNext: 3-Rectangle
         * [2024-10-25T15:02:41.317+09:00][ INFO] [main] # onNext: 5-Rectangle
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.just("1-Circle", "3-Circle", "5-Circle")
                .map { it.replace("Circle", "Rectangle") }
                .subscribe { log.info("# onNext: $it") }
        }
    }
}
