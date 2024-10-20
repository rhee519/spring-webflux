package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_9 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-20T23:12:42.223+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-20T23:12:42.228+09:00][ INFO] [main] # onNext: 0
         * [2024-10-20T23:12:42.229+09:00][ INFO] [main] # onNext: 1
         * [2024-10-20T23:12:42.229+09:00][ INFO] [main] # onNext: 2
         * [2024-10-20T23:12:42.229+09:00][ INFO] [main] # onNext: 3
         * [2024-10-20T23:12:42.229+09:00][ INFO] [main] # onNext: 4
         * [2024-10-20T23:12:42.229+09:00][ INFO] [main] # onNext: 5
         * [2024-10-20T23:12:42.229+09:00][ INFO] [main] # onNext: 6
         * [2024-10-20T23:12:42.229+09:00][ INFO] [main] # onNext: 7
         * [2024-10-20T23:12:42.229+09:00][ INFO] [main] # onNext: 8
         * [2024-10-20T23:12:42.229+09:00][ INFO] [main] # onNext: 9
         * [2024-10-20T23:12:42.229+09:00][ INFO] [main] # onNext: 10
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.generate({ 0 }) { state, sink ->
                sink.next(state)
                if (state == 10)
                    sink.complete()
                state + 1
            }.subscribe {
                log.info("# onNext: $it")
            }
        }
    }
}
