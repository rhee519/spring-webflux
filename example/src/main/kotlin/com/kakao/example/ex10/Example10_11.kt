package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Slf4j
class Example10_11 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Scheduler example (Schedulers.newSingle())
         * 호출될 때마다 신규 thread 생성
         * - 신규 생성할 thread의 daemon 여부는 설정 가능 (daemon: main 종료 시 함께 종료되는 보조 thread)
         *
         * output:
         * [2024-09-21T16:19:09.240+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T16:19:09.266+09:00][ INFO] [new-single-1] # [task1] doOnNext filter(): 5
         * [2024-09-21T16:19:09.266+09:00][ INFO] [new-single-2] # [task2] doOnNext filter(): 5
         * [2024-09-21T16:19:09.266+09:00][ INFO] [new-single-1] # [task1] doOnNext map(): 50
         * [2024-09-21T16:19:09.266+09:00][ INFO] [new-single-2] # [task2] doOnNext map(): 50
         * [2024-09-21T16:19:09.266+09:00][ INFO] [new-single-1] # onNext: 50
         * [2024-09-21T16:19:09.266+09:00][ INFO] [new-single-2] # onNext: 50
         * [2024-09-21T16:19:09.266+09:00][ INFO] [new-single-1] # [task1] doOnNext filter(): 7
         * [2024-09-21T16:19:09.266+09:00][ INFO] [new-single-2] # [task2] doOnNext filter(): 7
         * [2024-09-21T16:19:09.266+09:00][ INFO] [new-single-1] # [task1] doOnNext map(): 70
         * [2024-09-21T16:19:09.267+09:00][ INFO] [new-single-1] # onNext: 70
         * [2024-09-21T16:19:09.266+09:00][ INFO] [new-single-2] # [task2] doOnNext map(): 70
         * [2024-09-21T16:19:09.267+09:00][ INFO] [new-single-2] # onNext: 70
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
                .publishOn(Schedulers.newSingle("new-single", true))
                .filter { it > 3 }
                .doOnNext { logger.info("# [$taskName] doOnNext filter(): $it") }
                .map { it * 10 }
                .doOnNext { logger.info("# [$taskName] doOnNext map(): $it") }
        }
    }
}