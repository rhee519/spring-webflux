package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_11 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-20T23:20:55.823+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-20T23:20:55.859+09:00][ INFO] [main] # onNext: [2015,557603]
         * [2024-10-20T23:20:55.860+09:00][ INFO] [main] # onNext: [2016,1111811]
         * [2024-10-20T23:20:55.860+09:00][ INFO] [main] # onNext: [2017,22483583]
         * [2024-10-20T23:20:55.860+09:00][ INFO] [main] # onNext: [2018,19521543]
         * [2024-10-20T23:20:55.860+09:00][ INFO] [main] # onNext: [2019,15761568]
         * [2024-10-20T23:20:55.860+09:00][ INFO] [main] # onNext: [2020,22439002]
         * [2024-10-20T23:20:55.860+09:00][ INFO] [main] # onNext: [2021,63364000]
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val btcPricesMap = SampleData.btcTopPricesPerYearMap()

            Flux.generate({ 2015 }) { state, sink ->
                if (state > 2021)
                    sink.complete()
                else {
                    sink.next(btcPricesMap[state])
                }

                state + 1
            }.subscribe {
                log.info("# onNext: $it")
            }
        }
    }
}
