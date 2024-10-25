package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_31_Flux_concat {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * concat(): 여러 개의 Flux를 순차적으로 실행
         *
         * output:
         * [2024-10-25T15:31:23.403+09:00] DEBUG [main] Using Slf4j logging framework
         * [2024-10-25T15:31:23.410+09:00] INFO  [main] 1
         * [2024-10-25T15:31:23.411+09:00] INFO  [main] 2
         * [2024-10-25T15:31:23.411+09:00] INFO  [main] 3
         * [2024-10-25T15:31:23.411+09:00] INFO  [main] 4
         * [2024-10-25T15:31:23.411+09:00] INFO  [main] 5
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.concat(Flux.just(1, 2, 3), Flux.just(4, 5))
                .subscribe { log.info("$it") }
        }
    }
}
