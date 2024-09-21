package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Slf4j
class Example10_10 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Scheduler example (Schedulers.single())
         * thread를 하나 생성해서 Scheduler가 제거되기 전까지 재사용
         * - Schedulers.single()을 두 번 호출하더라도 하나의 thread만 사용
         *
         * output:
         * [2024-09-21T16:16:37.799+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T16:16:37.821+09:00][ INFO] [single-1] # [task1] doOnNext filter(): 5
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # [task1] doOnNext map(): 50
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # onNext: 50
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # [task1] doOnNext filter(): 7
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # [task1] doOnNext map(): 70
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # onNext: 70
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # [task2] doOnNext filter(): 5
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # [task2] doOnNext map(): 50
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # onNext: 50
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # [task2] doOnNext filter(): 7
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # [task2] doOnNext map(): 70
         * [2024-09-21T16:16:37.822+09:00][ INFO] [single-1] # onNext: 70
         */
        @JvmStatic
        fun main(args: Array<String>) {
            doTask("task1")
                .subscribe { logger.info("# onNext: $it") }
            doTask("task2")
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }

        private fun doTask(taskName: String): Flux<Int> {
            return Flux.fromIterable(listOf(1, 3, 5, 7))
                .publishOn(Schedulers.single())
                .filter { it > 3 }
                .doOnNext { logger.info("# [$taskName] doOnNext filter(): $it") }
                .map { it * 10 }
                .doOnNext { logger.info("# [$taskName] doOnNext map(): $it") }
        }
    }
}