package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_15 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-25T14:20:06.682+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T14:20:06.688+09:00][ INFO] [main] # onNext: 1
         * [2024-10-25T14:20:06.689+09:00][ INFO] [main] # onNext: 3
         * [2024-10-25T14:20:06.689+09:00][ INFO] [main] # onNext: 5
         * [2024-10-25T14:20:06.689+09:00][ INFO] [main] # onNext: 7
         * [2024-10-25T14:20:06.689+09:00][ INFO] [main] # onNext: 9
         * [2024-10-25T14:20:06.689+09:00][ INFO] [main] # onNext: 11
         * [2024-10-25T14:20:06.689+09:00][ INFO] [main] # onNext: 13
         * [2024-10-25T14:20:06.689+09:00][ INFO] [main] # onNext: 15
         * [2024-10-25T14:20:06.689+09:00][ INFO] [main] # onNext: 17
         * [2024-10-25T14:20:06.689+09:00][ INFO] [main] # onNext: 19
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.range(1, 20)
                .filter { it % 2 != 0 }
                .subscribe { log.info("# onNext: $it") }
        }
    }
}
