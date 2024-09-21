package com.kakao.example.ex9

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Sinks

@Slf4j
class Example9_4 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Sinks.one() Example
         * 처음 emit한 데이터는 모든 subscriber에게 전달되지만, 이후 emit한 데이터들은 drop된다.
         *
         * output:
         * [2024-09-18T19:26:56.380+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-18T19:26:56.383+09:00][DEBUG] [main] onNextDropped: Bye, Reactor!
         * [2024-09-18T19:26:56.385+09:00][ INFO] [main] # Subscriber 1: Hello, Reactor!
         * [2024-09-18T19:26:56.385+09:00][ INFO] [main] # Subscriber 2: Hello, Reactor!
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val sinkOne = Sinks.one<String>()
            val mono = sinkOne.asMono()

            sinkOne.emitValue("Hello, Reactor!", Sinks.EmitFailureHandler.FAIL_FAST)
            sinkOne.emitValue("Bye, Reactor!", Sinks.EmitFailureHandler.FAIL_FAST) // drop
            mono.subscribe { logger.info("# Subscriber 1: $it") }
            mono.subscribe { logger.info("# Subscriber 2: $it") }
        }
    }
}