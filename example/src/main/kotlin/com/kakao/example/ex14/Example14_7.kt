package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.LocalDateTime

class Example14_7 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output 1:
         * [2024-10-01T21:18:07.407+09:00][ INFO] [main] # start: 2024-10-01T21:18:07.405796
         * [2024-10-01T21:18:07.429+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-01T21:18:07.435+09:00][ INFO] [main] # Say Hi
         * [2024-10-01T21:18:10.453+09:00][ INFO] [parallel-1] # onNext: Hello
         *
         * output 2:
         * [2024-10-01T21:16:56.828+09:00][ INFO] [main] # start: 2024-10-01T21:16:56.827454
         * [2024-10-01T21:16:56.852+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-01T21:16:59.867+09:00][ INFO] [parallel-1] # onNext: Hello
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            log.info("# start: ${LocalDateTime.now()}")
            Mono.just("Hello")
                .delayElement(Duration.ofSeconds(3))
                .switchIfEmpty(sayDefault()) // output 1
//                .switchIfEmpty(Mono.defer { sayDefault() }) // output 2
                .subscribe { log.info("# onNext: $it") }

            Thread.sleep(3500)
        }

        private fun sayDefault(): Mono<String> {
            log.info("# Say Hi")
            return Mono.just("Hi")
        }
    }
}