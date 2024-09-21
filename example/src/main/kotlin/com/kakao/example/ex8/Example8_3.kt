package com.kakao.example.ex8

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Slf4j
class Example8_3 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * BackPressure Drop Example
         * drop되는 데이터를 핸들링할 수 있음
         *
         * output:
         * 18:29:40.726 [parallel-1] INFO com.kakao.example.ex8.Example8_3$Companion -- # onNext: 0
         * 18:29:40.733 [parallel-1] INFO com.kakao.example.ex8.Example8_3$Companion -- # onNext: 1
         * 18:29:40.740 [parallel-1] INFO com.kakao.example.ex8.Example8_3$Companion -- # onNext: 2
         * ...
         * 18:29:40.971 [parallel-1] INFO com.kakao.example.ex8.Example8_3$Companion -- # onNext: 40
         * 18:29:40.975 [parallel-2] INFO com.kakao.example.ex8.Example8_3$Companion -- # Dropped: 256
         * 18:29:40.975 [parallel-2] INFO com.kakao.example.ex8.Example8_3$Companion -- # Dropped: 257
         * ...
         * 18:29:42.721 [parallel-2] INFO com.kakao.example.ex8.Example8_3$Companion -- # Dropped: 2003
         * 18:29:42.722 [parallel-1] INFO com.kakao.example.ex8.Example8_3$Companion -- # onNext: 1239
         * 18:29:42.722 [parallel-2] INFO com.kakao.example.ex8.Example8_3$Companion -- # Dropped: 2004
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.interval(Duration.ofMillis(1))
                .onBackpressureDrop { logger.info("# Dropped: $it") }
                .publishOn(Schedulers.parallel())
                .subscribe(
                    {
                        try {
                            Thread.sleep(5)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        logger.info("# onNext: $it")
                    },
                    { error -> logger.error("# onError: $error") },
                    { logger.info("# onComplete") }
                )

            Thread.sleep(2000)
        }
    }
}