package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import java.time.LocalDateTime

class Example14_6 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-01T21:12:23.346+09:00][ INFO] [main] # start: 2024-10-01T21:12:23.346076
         * [2024-10-01T21:12:23.367+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-01T21:12:25.387+09:00][ INFO] [main] # onNext just1: 2024-10-01T21:12:23.347864
         * [2024-10-01T21:12:25.388+09:00][ INFO] [main] # onNext defer1: 2024-10-01T21:12:25.388526
         * [2024-10-01T21:12:27.395+09:00][ INFO] [main] # onNext just2: 2024-10-01T21:12:23.347864
         * [2024-10-01T21:12:27.396+09:00][ INFO] [main] # onNext defer2: 2024-10-01T21:12:27.396216
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            log.info("# start: ${LocalDateTime.now()}")
            val justMono = Mono.just(LocalDateTime.now())
            val deferMono = Mono.defer { Mono.just(LocalDateTime.now()) }

            Thread.sleep(2000)

            justMono.subscribe { log.info("# onNext just1: $it") }
            deferMono.subscribe { log.info("# onNext defer1: $it") }

            Thread.sleep(2000)

            justMono.subscribe { log.info("# onNext just2: $it") }
            deferMono.subscribe { log.info("# onNext defer2: $it") }
        }
    }
}