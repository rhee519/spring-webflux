package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

class Example14_30_Flux_flatMap2_async {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * flatMap() 내부에서 비동기 실행 가능 (단, 실행 순서 보장 안 됨)
         *
         * output:
         * [2024-10-25T15:27:08.154+09:00] DEBUG [main] Using Slf4j logging framework
         * [2024-10-25T15:27:08.182+09:00] INFO  [parallel-7] 8 * 1 = 8
         * ...
         * [2024-10-25T15:27:08.183+09:00] INFO  [parallel-7] 8 * 9 = 72
         * [2024-10-25T15:27:08.183+09:00] INFO  [parallel-8] 2 * 1 = 2
         * [2024-10-25T15:27:08.183+09:00] INFO  [parallel-8] 3 * 1 = 3
         * ...
         * [2024-10-25T15:27:08.183+09:00] INFO  [parallel-8] 3 * 9 = 27
         * [2024-10-25T15:27:08.183+09:00] INFO  [parallel-8] 4 * 1 = 4
         * ...
         * [2024-10-25T15:27:08.183+09:00] INFO  [parallel-8] 4 * 9 = 36
         * [2024-10-25T15:27:08.183+09:00] INFO  [parallel-8] 5 * 1 = 5
         * ...
         * [2024-10-25T15:27:08.184+09:00] INFO  [parallel-8] 5 * 9 = 45
         * [2024-10-25T15:27:08.184+09:00] INFO  [parallel-8] 6 * 1 = 6
         * ...
         * [2024-10-25T15:27:08.184+09:00] INFO  [parallel-8] 6 * 9 = 54
         * [2024-10-25T15:27:08.184+09:00] INFO  [parallel-8] 7 * 1 = 7
         * ...
         * [2024-10-25T15:27:08.184+09:00] INFO  [parallel-8] 7 * 9 = 63
         * [2024-10-25T15:27:08.184+09:00] INFO  [parallel-8] 9 * 1 = 9
         * [2024-10-25T15:27:08.184+09:00] INFO  [parallel-8] 2 * 2 = 4
         * ...
         * [2024-10-25T15:27:08.184+09:00] INFO  [parallel-8] 2 * 9 = 18
         * [2024-10-25T15:27:08.184+09:00] INFO  [parallel-8] 9 * 2 = 18
         * ...
         * [2024-10-25T15:27:08.184+09:00] INFO  [parallel-8] 9 * 9 = 81
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.range(2, 8)
                .flatMap { left ->
                    Flux.range(1, 9)
                        .publishOn(Schedulers.parallel())
                        .map { right -> "$left * $right = ${left * right}" }
                }
                .subscribe(log::info)

            Thread.sleep(500)
        }
    }
}
