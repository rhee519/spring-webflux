package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import java.time.Duration

class Example14_22_Flux_take2_duration {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-25T14:45:23.333+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T14:45:24.359+09:00][ INFO] [parallel-2] # onNext: 0
         * [2024-10-25T14:45:25.360+09:00][ INFO] [parallel-2] # onNext: 1
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.interval(Duration.ofSeconds(1))
                .take(Duration.ofMillis(2500))
                .subscribe { log.info("# onNext: $it") }

            Thread.sleep(3000)
        }
    }
}
