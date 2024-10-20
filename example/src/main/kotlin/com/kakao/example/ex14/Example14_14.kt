package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.core.scheduler.Schedulers

class Example14_14 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private var start = 1
        private var end = 4

        /**
         * ```
         * output:
         * [2024-10-20T23:42:17.363+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-20T23:42:18.371+09:00][ INFO] [main] # onNext: 1
         * [2024-10-20T23:42:18.372+09:00][ INFO] [main] # onNext: 2
         * [2024-10-20T23:42:19.376+09:00][ INFO] [main] # onNext: 3
         * [2024-10-20T23:42:19.376+09:00][ INFO] [main] # onNext: 4
         * [2024-10-20T23:42:20.379+09:00][ INFO] [main] # onNext: 5
         * [2024-10-20T23:42:20.380+09:00][ INFO] [main] # onNext: 6
         * [2024-10-20T23:42:21.381+09:00][ INFO] [main] # onNext: 7
         * [2024-10-20T23:42:21.381+09:00][ INFO] [main] # onNext: 8
         * [2024-10-20T23:42:22.386+09:00][ INFO] [main] # onNext: 9
         * [2024-10-20T23:42:22.386+09:00][ INFO] [main] # onNext: 10
         * [2024-10-20T23:42:23.387+09:00][ INFO] [main] # onComplete
         * [2024-10-20T23:42:23.389+09:00][ INFO] [main] # clean up
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.create(
                { sink ->
                    sink.onRequest { cnt ->
                        log.info("# requested: $cnt")
                        try {
                            Thread.sleep(500)
                            (start..end).forEach { sink.next(it) }
                            start += 4
                            end += 4
                        } catch (e: InterruptedException) {
                            sink.error(e)
                        }
                    }

                    sink.onDispose { log.info("clean up") }
                },
                FluxSink.OverflowStrategy.DROP
            )
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(
                    Schedulers.parallel(),
                    2  // request 당 2개씩 consume하지만, 4개씩 emit되므로 나머지 2건은 OverflowStrategy.DROP에 의해 버려짐
                )
                .subscribe { log.info("# onNext: $it") }

            Thread.sleep(3000)
        }
    }
}
