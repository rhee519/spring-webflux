package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Slf4j
class Example10_12 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Scheduler example (Schedulers.boundedElastic())
         * ExecutorService 기반의 thread pool을 생성하여, 그 안에서 정해진 수만큼의 thread를 사용
         * 작업이 종료된 thread는 다시 pool에 반환되어 재사용
         * 기본적으로 CPU 코어 수 * 10개의 thread를 생성
         * 모든 thread가 작업 중이라면 최대 100,000개의 작업이 큐에서 대기할 수 있음
         *
         * output:
         * [2024-09-21T16:23:21.133+09:00][ INFO] [boundedElastic-1] # [task1] doOnNext filter(): 5
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-2] # [task2] doOnNext filter(): 5
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-1] # [task1] doOnNext map(): 50
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-2] # [task2] doOnNext map(): 50
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-2] # onNext: 50
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-2] # [task2] doOnNext filter(): 7
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-1] # onNext: 50
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-2] # [task2] doOnNext map(): 70
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-1] # [task1] doOnNext filter(): 7
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-1] # [task1] doOnNext map(): 70
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-2] # onNext: 70
         * [2024-09-21T16:23:21.134+09:00][ INFO] [boundedElastic-1] # onNext: 70
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-3] # [task3] doOnNext filter(): 5
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-3] # [task3] doOnNext map(): 50
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-3] # onNext: 50
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-3] # [task3] doOnNext filter(): 7
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-3] # [task3] doOnNext map(): 70
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-3] # onNext: 70
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-4] # [task4] doOnNext filter(): 5
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-4] # [task4] doOnNext map(): 50
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-4] # onNext: 50
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-4] # [task4] doOnNext filter(): 7
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-4] # [task4] doOnNext map(): 70
         * [2024-09-21T16:23:21.135+09:00][ INFO] [boundedElastic-4] # onNext: 70
         * [2024-09-21T16:23:21.136+09:00][ INFO] [boundedElastic-4] # [task5] doOnNext filter(): 5
         * [2024-09-21T16:23:21.136+09:00][ INFO] [boundedElastic-4] # [task5] doOnNext map(): 50
         * [2024-09-21T16:23:21.136+09:00][ INFO] [boundedElastic-4] # onNext: 50
         * [2024-09-21T16:23:21.136+09:00][ INFO] [boundedElastic-4] # [task5] doOnNext filter(): 7
         * [2024-09-21T16:23:21.136+09:00][ INFO] [boundedElastic-4] # [task5] doOnNext map(): 70
         * [2024-09-21T16:23:21.136+09:00][ INFO] [boundedElastic-4] # onNext: 70
         */
        @JvmStatic
        fun main(args: Array<String>) {
            doTask("task1")
                .subscribe { logger.info("# onNext: $it") }
            doTask("task2")
                .subscribe { logger.info("# onNext: $it") }
            doTask("task3")
                .subscribe { logger.info("# onNext: $it") }
            doTask("task4")
                .subscribe { logger.info("# onNext: $it") }
            doTask("task5")
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }

        private fun doTask(taskName: String): Flux<Int> {
            return Flux.fromIterable(listOf(1, 3, 5, 7))
                .publishOn(Schedulers.boundedElastic())
                .filter { it > 3 }
                .doOnNext { logger.info("# [$taskName] doOnNext filter(): $it") }
                .map { it * 10 }
                .doOnNext { logger.info("# [$taskName] doOnNext map(): $it") }
        }
    }
}