package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_2 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-01T20:45:15.782+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-01T20:45:15.819+09:00][ INFO] [main] coin명: BTC, 현재가: 52000000
         * [2024-10-01T20:45:15.820+09:00][ INFO] [main] coin명: ETH, 현재가: 1720000
         * [2024-10-01T20:45:15.820+09:00][ INFO] [main] coin명: XRP, 현재가: 533
         * [2024-10-01T20:45:15.820+09:00][ INFO] [main] coin명: ICX, 현재가: 2080
         * [2024-10-01T20:45:15.820+09:00][ INFO] [main] coin명: EOS, 현재가: 4020
         * [2024-10-01T20:45:15.820+09:00][ INFO] [main] coin명: BCH, 현재가: 558000
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(SampleData.coins)
                .subscribe { log.info("coin명: ${it.t1}, 현재가: ${it.t2}") }
        }
    }
}