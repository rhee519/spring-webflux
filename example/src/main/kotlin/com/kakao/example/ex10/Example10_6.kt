package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Slf4j
class Example10_6 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * publishOn()만 사용하는 예제
         * publishOn() 이후에 추가된 Operator 체인은 모두 parallel thread에서 실행됨
         *
         * output:
         * [2024-09-21T16:00:17.518+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T16:00:17.545+09:00][ INFO] [main] # doOnNext fromIterable(): 1
         * [2024-09-21T16:00:17.546+09:00][ INFO] [main] # doOnNext fromIterable(): 3
         * [2024-09-21T16:00:17.546+09:00][ INFO] [main] # doOnNext fromIterable(): 5
         * [2024-09-21T16:00:17.546+09:00][ INFO] [main] # doOnNext fromIterable(): 7
         * [2024-09-21T16:00:17.546+09:00][ INFO] [parallel-1] # doOnNext filter(): 5
         * [2024-09-21T16:00:17.546+09:00][ INFO] [parallel-1] # doOnNext map(): 50
         * [2024-09-21T16:00:17.546+09:00][ INFO] [parallel-1] # onNext: 50
         * [2024-09-21T16:00:17.546+09:00][ INFO] [parallel-1] # doOnNext filter(): 7
         * [2024-09-21T16:00:17.546+09:00][ INFO] [parallel-1] # doOnNext map(): 70
         * [2024-09-21T16:00:17.546+09:00][ INFO] [parallel-1] # onNext: 70
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(listOf(1, 3, 5, 7))
                .doOnNext { logger.info("# doOnNext fromIterable(): $it") }
                .publishOn(Schedulers.parallel())
                .filter { it > 3 }
                .doOnNext { logger.info("# doOnNext filter(): $it") }
                .map { it * 10 }
                .doOnNext { logger.info("# doOnNext map(): $it") }
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}