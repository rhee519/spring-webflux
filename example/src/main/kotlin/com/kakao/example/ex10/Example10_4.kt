package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Slf4j
class Example10_4 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * parallel() Example 2
         *
         * parallel(n): emit되는 데이터를 n개의 CPU core에 맞게 병렬로 분배 (n: parallelism)
         * runOn(): upstream에서 emit된 데이터를 처리할 논리 thread를 지정
         *
         * output:
         * [2024-09-21T15:56:05.904+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T15:56:05.934+09:00][ INFO] [parallel-2] # onNext: 3
         * [2024-09-21T15:56:05.935+09:00][ INFO] [parallel-2] # onNext: 11
         * [2024-09-21T15:56:05.935+09:00][ INFO] [parallel-2] # onNext: 19
         * [2024-09-21T15:56:05.934+09:00][ INFO] [parallel-1] # onNext: 1
         * [2024-09-21T15:56:05.935+09:00][ INFO] [parallel-1] # onNext: 9
         * [2024-09-21T15:56:05.935+09:00][ INFO] [parallel-1] # onNext: 17
         * [2024-09-21T15:56:05.934+09:00][ INFO] [parallel-3] # onNext: 5
         * [2024-09-21T15:56:05.935+09:00][ INFO] [parallel-3] # onNext: 13
         * [2024-09-21T15:56:05.934+09:00][ INFO] [parallel-4] # onNext: 7
         * [2024-09-21T15:56:05.935+09:00][ INFO] [parallel-4] # onNext: 15
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19))
                .parallel(4) // 사용할 CPU core 수를 지정
                .runOn(Schedulers.parallel()) // 지정하지 않으면 모두 main thread에서 실행됨
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}