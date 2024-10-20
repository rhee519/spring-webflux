package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

class Example14_13 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

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
            val priceEmitter = CryptoCurrencyPriceEmitter()
            Flux.create { sink ->
                priceEmitter.setListener(object : CryptoCurrencyPriceListener {
                    override fun onPrice(priceList: List<Int>) {
                        priceList.stream().forEach { sink.next(it) }
                    }

                    override fun onComplete() {
                        sink.complete()
                    }
                })
            }
                .publishOn(Schedulers.parallel())
                .subscribe(
                    { log.info("# onNext: $it") },
                    { error -> log.error("# onError: ${error.message}") },
                    { log.info("# onComplete") }
                )

            Thread.sleep(3000)
            priceEmitter.flowInto()
            priceEmitter.complete()
            Thread.sleep(1000)
        }
    }
}
