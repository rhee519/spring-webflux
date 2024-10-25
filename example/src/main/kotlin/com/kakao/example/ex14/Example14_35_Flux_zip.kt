package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import java.time.Duration

class Example14_35_Flux_zip {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * zip(): 여러 개의 datasource 들로부터 emit된 데이터들을 묶어서 하나씩 emit
         *
         * output:
         * [2024-10-25T16:01:23.657+09:00] DEBUG [main] Using Slf4j logging framework
         * [2024-10-25T16:01:24.193+09:00] INFO  [parallel-2] # onNext: [1,10001]
         * [2024-10-25T16:01:24.700+09:00] INFO  [parallel-4] # onNext: [2,10002]
         * [2024-10-25T16:01:25.206+09:00] INFO  [parallel-6] # onNext: [3,10003]
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.zip(
                Flux.just(1, 2, 3).delayElements(Duration.ofMillis(300)),
                Flux.just(10001, 10002, 10003, 10004).delayElements(Duration.ofMillis(500)),
            )
                .subscribe { log.info("# onNext: $it") }

            Thread.sleep(2500)
        }
    }
}
