package com.kakao.example

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Sinks

@Slf4j
class Example9_8 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Sinks.many() Example
         *
         *
         * output:
         * [2024-09-18T19:31:18.887+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-18T19:31:18.892+09:00][ INFO] [main] # Subscriber1: 1
         * [2024-09-18T19:31:18.892+09:00][ INFO] [main] # Subscriber1: 2
         * [2024-09-18T19:31:18.892+09:00][ INFO] [main] # Subscriber1: 3
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val unicastSink = Sinks.many().unicast().onBackpressureBuffer<Int>()
            val fluxView = unicastSink.asFlux()

            unicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST)
            unicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST)
            fluxView.subscribe { logger.info("# Subscriber1: $it") }

            unicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST)

            /**
             * subscriber를 하나 더 추가하면 에러 발생
             * [2024-09-18T19:30:09.432+09:00][ERROR] [main] Operator called default onErrorDropped
             * reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.IllegalStateException: Sinks.many().unicast() sinks only allow a single Subscriber
             * Caused by: java.lang.IllegalStateException: Sinks.many().unicast() sinks only allow a single Subscriber
             * 	at reactor.core.publisher.SinkManyUnicast.subscribe(SinkManyUnicast.java:426)
             * 	at reactor.core.publisher.Flux.subscribe(Flux.java:8840)
             * 	at reactor.core.publisher.Flux.subscribeWith(Flux.java:8961)
             * 	at reactor.core.publisher.Flux.subscribe(Flux.java:8805)
             * 	at reactor.core.publisher.Flux.subscribe(Flux.java:8729)
             * 	at reactor.core.publisher.Flux.subscribe(Flux.java:8672)
             * 	at com.kakao.example.Example9_8$Companion.main(Example9_8.kt:27)
             * 	at com.kakao.example.Example9_8.main(Example9_8.kt)
             */
//            fluxView.subscribe { logger.info("# Subscriber2: $it") }
        }
    }
}