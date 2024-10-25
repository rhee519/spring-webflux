package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import java.time.Duration

class Example14_21 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-25T14:40:02.706+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T14:40:03.733+09:00][ INFO] [parallel-1] # onNext: 0
         * [2024-10-25T14:40:04.727+09:00][ INFO] [parallel-1] # onNext: 1
         * [2024-10-25T14:40:05.727+09:00][ INFO] [parallel-1] # onNext: 2
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.interval(Duration.ofSeconds(1))
                .take(3)
                .subscribe { log.info("# onNext: $it") }

            Thread.sleep(4000)
        }
    }
}
