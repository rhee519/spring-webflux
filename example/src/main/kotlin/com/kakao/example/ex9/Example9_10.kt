package com.kakao.example.ex9

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Sinks

@Slf4j
class Example9_10 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Sinks.many() Example (replay)
         *
         * output:
         * [2024-09-21T15:32:16.741+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T15:32:16.747+09:00][ INFO] [main] # Subscriber1: 2
         * [2024-09-21T15:32:16.747+09:00][ INFO] [main] # Subscriber1: 3
         * [2024-09-21T15:32:16.747+09:00][ INFO] [main] # Subscriber1: 4
         * [2024-09-21T15:32:16.748+09:00][ INFO] [main] # Subscriber2: 3
         * [2024-09-21T15:32:16.748+09:00][ INFO] [main] # Subscriber2: 4
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val replaySink = Sinks.many().replay().limit<Int>(2) // 최근 emit된 2개의 데이터부터 replay
            val fluxView = replaySink.asFlux()

            replaySink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST)
            replaySink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST)
            replaySink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST)

            fluxView.subscribe { logger.info("# Subscriber1: $it") }

            replaySink.emitNext(4, Sinks.EmitFailureHandler.FAIL_FAST)
            fluxView.subscribe { logger.info("# Subscriber2: $it") }
        }
    }
}