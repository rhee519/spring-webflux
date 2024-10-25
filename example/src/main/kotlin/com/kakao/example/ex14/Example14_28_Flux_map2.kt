package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_28_Flux_map2 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private const val BUY_PRICE = 50_000_000

        /**
         * ```
         * output:
         * [2024-10-25T15:10:17.067+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T15:10:17.100+09:00][ INFO] [main] # doOnNext: [2021,63364000]
         * [2024-10-25T15:10:17.101+09:00][ INFO] [main] # onNext: 26.728 %
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(SampleData.btcTopPricesPerYear)
                .filter { it.t1 == 2021 }
                .doOnNext { log.info("# doOnNext: $it") }
                .map { calculateProfitRate(BUY_PRICE.toDouble(), it.t2) }
                .subscribe { log.info("# onNext: $it %") }
        }

        private fun calculateProfitRate(buyPrice: Double, topPrice: Long): Double {
            return (topPrice - buyPrice) / buyPrice * 100
        }
    }
}
