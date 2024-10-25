package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import java.time.Duration

class Example14_36_Flux_zip2_lambda {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * zip(): lambda를 사용하여 여러 개의 Flux를 묶어서 emit
         *
         * output:
         * [2024-10-25T16:08:37.980+09:00] DEBUG [main] Using Slf4j logging framework
         * [2024-10-25T16:08:38.519+09:00] INFO  [parallel-2] # onNext: 4
         * [2024-10-25T16:08:39.020+09:00] INFO  [parallel-4] # onNext: 10
         * [2024-10-25T16:08:39.524+09:00] INFO  [parallel-6] # onNext: 18
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.zip(
                Flux.just(1, 2, 3).delayElements(Duration.ofMillis(300)),
                Flux.just(4, 5, 6).delayElements(Duration.ofMillis(500)),
            ) { n1, n2 -> n1 * n2 }
                .subscribe { log.info("# onNext: $it") }

            Thread.sleep(2500)
        }
    }
}
