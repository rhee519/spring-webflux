package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import java.time.Duration

class Example14_19_Flux_skip2_duration {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-25T14:36:16.124+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T14:36:17.347+09:00][ INFO] [parallel-2] # onNext: 3
         * [2024-10-25T14:36:17.647+09:00][ INFO] [parallel-2] # onNext: 4
         * [2024-10-25T14:36:17.950+09:00][ INFO] [parallel-2] # onNext: 5
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.interval(Duration.ofMillis(300))
                .skip(Duration.ofSeconds(1))
                .subscribe { log.info("# onNext: $it") }

            Thread.sleep(2000)
        }
    }
}
