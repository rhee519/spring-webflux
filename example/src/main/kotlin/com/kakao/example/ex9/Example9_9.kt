package com.kakao.example.ex9

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Sinks

@Slf4j
class Example9_9 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Sinks.many() Example (multicast)
         *
         * output:
         * [2024-09-21T15:19:50.178+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T15:19:50.186+09:00][ INFO] [main] # Subscriber1: 1
         * [2024-09-21T15:19:50.186+09:00][ INFO] [main] # Subscriber1: 2
         * [2024-09-21T15:19:50.187+09:00][ INFO] [main] # Subscriber1: 3
         * [2024-09-21T15:19:50.187+09:00][ INFO] [main] # Subscriber2: 3
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val multicastSink = Sinks.many().multicast().onBackpressureBuffer<Int>()
            val fluxView = multicastSink.asFlux()

            multicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST)
            multicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST)
            fluxView.subscribe { logger.info("# Subscriber1: $it") }
            fluxView.subscribe { logger.info("# Subscriber2: $it") }
            multicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST)
        }
    }
}