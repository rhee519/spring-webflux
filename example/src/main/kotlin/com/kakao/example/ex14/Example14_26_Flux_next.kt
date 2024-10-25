package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_26_Flux_next {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * next(): upstream 데이터 중 첫 번째 데이터만 emit
         *
         * output:
         * [2024-10-25T14:50:21.227+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T14:50:21.261+09:00][ INFO] [main] 2010: 565
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(SampleData.btcTopPricesPerYear)
                .next()
                .subscribe { log.info("${it.t1}: ${it.t2}") }
        }
    }
}
