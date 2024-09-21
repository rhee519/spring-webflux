package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Slf4j
class Example10_9 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Scheduler example (Schedulers.immediate())
         * upstream의 thread에서 그대로 작업
         *
         * output:
         * [2024-09-21T16:10:30.534+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T16:10:30.567+09:00][ INFO] [parallel-1] # doOnNext filter(): 5
         * [2024-09-21T16:10:30.568+09:00][ INFO] [parallel-1] # doOnNext map(): 50
         * [2024-09-21T16:10:30.568+09:00][ INFO] [parallel-1] # onNext: 50
         * [2024-09-21T16:10:30.568+09:00][ INFO] [parallel-1] # doOnNext filter(): 7
         * [2024-09-21T16:10:30.568+09:00][ INFO] [parallel-1] # doOnNext map(): 70
         * [2024-09-21T16:10:30.568+09:00][ INFO] [parallel-1] # onNext: 70
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(listOf(1, 3, 5, 7))
                .publishOn(Schedulers.parallel())
                .filter { it > 3 }
                .doOnNext { logger.info("# doOnNext filter(): $it") }
                .publishOn(Schedulers.immediate())
                .map { it * 10 }
                .doOnNext { logger.info("# doOnNext map(): $it") }
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}