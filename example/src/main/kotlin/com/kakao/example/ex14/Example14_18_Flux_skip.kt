package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import java.time.Duration

class Example14_18_Flux_skip {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-25T14:34:04.306+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T14:34:07.343+09:00][ INFO] [parallel-1] # onNext: 2
         * [2024-10-25T14:34:08.344+09:00][ INFO] [parallel-1] # onNext: 3
         * [2024-10-25T14:34:09.344+09:00][ INFO] [parallel-1] # onNext: 4
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.interval(Duration.ofSeconds(1))
                .skip(2)
                .subscribe { log.info("# onNext: $it") }

            Thread.sleep(5500)
        }
    }
}
