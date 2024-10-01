package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_5 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-01T21:09:41.338+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-01T21:09:41.368+09:00][ INFO] [main] 2017's 22483583
         * [2024-10-01T21:09:41.369+09:00][ INFO] [main] 2018's 19521543
         * [2024-10-01T21:09:41.369+09:00][ INFO] [main] 2019's 15761568
         * [2024-10-01T21:09:41.369+09:00][ INFO] [main] 2020's 22439002
         * [2024-10-01T21:09:41.369+09:00][ INFO] [main] 2021's 63364000
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.range(7, 5)
                .mapNotNull { SampleData.btcTopPricesPerYear[it] }
                .subscribe { it?.let { log.info("${it.t1}'s ${it.t2}") } }
        }
    }
}