package com.kakao.example.ex9

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Sinks
import reactor.core.scheduler.Schedulers
import java.util.stream.IntStream

@Slf4j
class Example9_2 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * output:
         * [2024-09-18T19:20:27.542+09:00][ INFO] [Thread-0] # emitted: 1
         * [2024-09-18T19:20:27.644+09:00][ INFO] [Thread-1] # emitted: 2
         * [2024-09-18T19:20:27.747+09:00][ INFO] [Thread-2] # emitted: 3
         * [2024-09-18T19:20:27.852+09:00][ INFO] [Thread-3] # emitted: 4
         * [2024-09-18T19:20:27.958+09:00][ INFO] [Thread-4] # emitted: 5
         * [2024-09-18T19:20:28.100+09:00][ INFO] [parallel-2] # map(): task 1 result success!
         * [2024-09-18T19:20:28.101+09:00][ INFO] [parallel-2] # map(): task 2 result success!
         * [2024-09-18T19:20:28.101+09:00][ INFO] [parallel-2] # map(): task 3 result success!
         * [2024-09-18T19:20:28.101+09:00][ INFO] [parallel-2] # map(): task 4 result success!
         * [2024-09-18T19:20:28.101+09:00][ INFO] [parallel-2] # map(): task 5 result success!
         * [2024-09-18T19:20:28.101+09:00][ INFO] [parallel-1] # onNext: task 1 result success!
         * [2024-09-18T19:20:28.101+09:00][ INFO] [parallel-1] # onNext: task 2 result success!
         * [2024-09-18T19:20:28.101+09:00][ INFO] [parallel-1] # onNext: task 3 result success!
         * [2024-09-18T19:20:28.101+09:00][ INFO] [parallel-1] # onNext: task 4 result success!
         * [2024-09-18T19:20:28.101+09:00][ INFO] [parallel-1] # onNext: task 5 result success!
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val tasks = 6
            val unicastSink = Sinks.many().unicast().onBackpressureBuffer<String>()
            val fluxView = unicastSink.asFlux()

            // 데이터를 emit하는 N개의 thread를 sink에 연결
            IntStream
                .range(1, tasks)
                .forEach {
                    try {
                        Thread {
                            unicastSink.emitNext(doTasks(it), Sinks.EmitFailureHandler.FAIL_FAST)
                            logger.info("# emitted: $it")
                        }.start()
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

            fluxView
                .publishOn(Schedulers.parallel())
                .map { "$it success!" }
                .doOnNext { logger.info("# map(): $it") }
                .publishOn(Schedulers.parallel())
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(200)
        }

        private fun doTasks(taskNum: Int): String {
            return "task $taskNum result"
        }
    }
}