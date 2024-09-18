package com.kakao.example

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

@Slf4j
class Example7_1 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Cold Sequence(Publisher) Example
         * - Cold Sequence는 Subscriber가 subscribe할 때마다 새로운 데이터를 생성하는 Publisher
         * - Cold Sequence는 Subscriber 간에 데이터를 공유하지 않고, 모두 동일한 thread에서 실행됨
         *
         * output:
         * 17:43:00.350 [main] INFO com.kakao.example.Example7_1$Companion -- # Subscriber 1: korea
         * 17:43:00.351 [main] INFO com.kakao.example.Example7_1$Companion -- # Subscriber 1: japan
         * 17:43:00.351 [main] INFO com.kakao.example.Example7_1$Companion -- # Subscriber 1: china
         * 17:43:00.351 [main] INFO com.kakao.example.Example7_1$Companion -- # Subscriber 1: usa
         * 17:43:00.351 [main] INFO com.kakao.example.Example7_1$Companion -- # Subscriber 1: canada
         * ----------
         * 17:43:02.357 [main] INFO com.kakao.example.Example7_1$Companion -- # Subscriber 2: korea
         * 17:43:02.358 [main] INFO com.kakao.example.Example7_1$Companion -- # Subscriber 2: japan
         * 17:43:02.358 [main] INFO com.kakao.example.Example7_1$Companion -- # Subscriber 2: china
         * 17:43:02.358 [main] INFO com.kakao.example.Example7_1$Companion -- # Subscriber 2: usa
         * 17:43:02.358 [main] INFO com.kakao.example.Example7_1$Companion -- # Subscriber 2: canada
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val coldFlux = Flux.fromIterable(listOf("KOREA", "JAPAN", "CHINA", "USA", "CANADA"))
                .map(String::lowercase)

            coldFlux.subscribe {
                logger.info("# Subscriber 1: $it")
            }

            println("----------")

            Thread.sleep(2000)
            coldFlux.subscribe {
                logger.info("# Subscriber 2: $it")
            }
        }
    }
}
