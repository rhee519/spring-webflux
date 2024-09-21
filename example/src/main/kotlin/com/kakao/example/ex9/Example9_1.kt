package com.kakao.example.ex9

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.stream.IntStream

@Slf4j
class Example9_1 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * output:
         * [2024-09-18T19:07:45.991+09:00][ INFO][boundedElastic-1] # create(): task 1 result
         * [2024-09-18T19:07:45.992+09:00][ INFO][boundedElastic-1] # create(): task 2 result
         * [2024-09-18T19:07:45.992+09:00][ INFO][boundedElastic-1] # create(): task 3 result
         * [2024-09-18T19:07:45.992+09:00][ INFO][boundedElastic-1] # create(): task 4 result
         * [2024-09-18T19:07:45.992+09:00][ INFO][boundedElastic-1] # create(): task 5 result
         * [2024-09-18T19:07:45.992+09:00][ INFO][parallel-2] # map(): task 1 result success!
         * [2024-09-18T19:07:45.993+09:00][ INFO][parallel-2] # map(): task 2 result success!
         * [2024-09-18T19:07:45.993+09:00][ INFO][parallel-2] # map(): task 3 result success!
         * [2024-09-18T19:07:45.993+09:00][ INFO][parallel-2] # map(): task 4 result success!
         * [2024-09-18T19:07:45.993+09:00][ INFO][parallel-2] # map(): task 5 result success!
         * [2024-09-18T19:07:45.993+09:00][ INFO][parallel-1] # onNext: task 1 result success!
         * [2024-09-18T19:07:45.993+09:00][ INFO][parallel-1] # onNext: task 2 result success!
         * [2024-09-18T19:07:45.993+09:00][ INFO][parallel-1] # onNext: task 3 result success!
         * [2024-09-18T19:07:45.993+09:00][ INFO][parallel-1] # onNext: task 4 result success!
         * [2024-09-18T19:07:45.993+09:00][ INFO][parallel-1] # onNext: task 5 result success!
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val tasks = 6

            Flux.create<String> { sink ->
                IntStream
                    .range(1, tasks)
                    .forEach { sink.next(doTasks(it)) }
            }
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext { logger.info("# create(): $it") }
                .publishOn(Schedulers.parallel())
                .map { "$it success!" }
                .doOnNext { logger.info("# map(): $it") }
                .publishOn(Schedulers.parallel())
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(1000)
        }

        private fun doTasks(taskNum: Int): String {
            return "task $taskNum result"
        }
    }
}