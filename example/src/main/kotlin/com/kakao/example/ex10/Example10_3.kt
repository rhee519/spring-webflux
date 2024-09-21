package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Slf4j
class Example10_3 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * parallel() Example
         *
         * parallel(): emit되는 데이터를 CPU의 물리 thread(논리 core) 수에 맞게 병렬로 분배
         * runOn(): upstream에서 emit된 데이터를 처리할 논리 thread를 지정
         *
         * output:
         * [2024-09-21T15:53:02.711+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T15:53:02.745+09:00][ INFO] [parallel-4] # onNext: 7
         * [2024-09-21T15:53:02.746+09:00][ INFO] [parallel-7] # onNext: 13
         * [2024-09-21T15:53:02.746+09:00][ INFO] [parallel-8] # onNext: 15
         * [2024-09-21T15:53:02.745+09:00][ INFO] [parallel-3] # onNext: 5
         * [2024-09-21T15:53:02.746+09:00][ INFO] [parallel-6] # onNext: 11
         * [2024-09-21T15:53:02.746+09:00][ INFO] [parallel-1] # onNext: 1
         * [2024-09-21T15:53:02.746+09:00][ INFO] [parallel-2] # onNext: 3
         * [2024-09-21T15:53:02.746+09:00][ INFO] [parallel-1] # onNext: 17
         * [2024-09-21T15:53:02.746+09:00][ INFO] [parallel-2] # onNext: 19
         * [2024-09-21T15:53:02.745+09:00][ INFO] [parallel-5] # onNext: 9
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19))
                .parallel()
                .runOn(Schedulers.parallel()) // 지정하지 않으면 모두 main thread에서 실행됨
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}