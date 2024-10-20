package com.kakao.example.ex14

import org.reactivestreams.Subscription
import org.slf4j.LoggerFactory
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux

class Example14_12 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private var SIZE = 0
        private var COUNT = -1
        private val DATA_SOURCE = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

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
            log.info("# start!")

            Flux.create { sink ->
                sink.onRequest { cnt ->
                    try {
                        Thread.sleep(1000)
                        repeat(cnt.toInt()) {
                            if (COUNT >= DATA_SOURCE.size - 1) sink.complete()
                            else sink.next(DATA_SOURCE[++COUNT])
                        }
                    } catch (e: InterruptedException) {
                        sink.error(e)
                    }
                }
                sink.onDispose { log.info("# clean up") }
            }.subscribe(object : BaseSubscriber<Int>() {
                // 데이터를 2개씩 consume하는 custom subscriber
                override fun hookOnSubscribe(subscription: Subscription) {
                    request(2)
                }

                override fun hookOnNext(value: Int) {
                    SIZE++
                    log.info("# onNext: $value")
                    if (SIZE == 2) {
                        request(2)
                        SIZE = 0
                    }
                }

                override fun hookOnComplete() {
                    log.info("# onComplete")
                }
            })
        }
    }
}
