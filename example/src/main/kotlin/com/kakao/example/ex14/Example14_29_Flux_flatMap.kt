package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_29_Flux_flatMap {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-25T15:14:14.783+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T15:14:14.791+09:00][ INFO] [main] Good Morning
         * [2024-10-25T15:14:14.792+09:00][ INFO] [main] Good Afternoon
         * [2024-10-25T15:14:14.792+09:00][ INFO] [main] Good Evening
         * [2024-10-25T15:14:14.792+09:00][ INFO] [main] Bad Morning
         * [2024-10-25T15:14:14.792+09:00][ INFO] [main] Bad Afternoon
         * [2024-10-25T15:14:14.792+09:00][ INFO] [main] Bad Evening
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.just("Good", "Bad")
                .flatMap { feeling ->
                    Flux.just("Morning", "Afternoon", "Evening")
                        .map { time -> "$feeling $time" }
                }
                .subscribe(log::info)
        }
    }
}
