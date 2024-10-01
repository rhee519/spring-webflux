package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

class Example14_1 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-01T20:24:48.040+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-01T20:24:48.042+09:00][ INFO] [main] # onComplete
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Mono.justOrEmpty<String>(null)
                .subscribe(
                    { data -> log.info("# data: $data") }, // data emit
                    { error -> error.printStackTrace() },
                    { log.info("# onComplete") }
                )
        }
    }
}