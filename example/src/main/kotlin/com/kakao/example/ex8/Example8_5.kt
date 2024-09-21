package com.kakao.example.ex8

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.BufferOverflowStrategy
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Slf4j
class Example8_5 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * BackPressure Buffer DROP_LATEST Example
         * buffer overflow가 발생한 경우, latest를 drop
         *
         * output:
         * 18:44:20.281 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # emitted by original Flux: 0
         * 18:44:20.286 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # [ emitted by buffer: 0 ]
         * 18:44:20.576 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # emitted by original Flux: 1
         * 18:44:20.874 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # emitted by original Flux: 2
         * 18:44:21.176 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # emitted by original Flux: 3
         * 18:44:21.179 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- ** overflow & dropped: 3 **
         * 18:44:21.293 [parallel-1] INFO com.kakao.example.ex8.Example8_5$Companion -- # onNext: 0
         * 18:44:21.294 [parallel-1] INFO com.kakao.example.ex8.Example8_5$Companion -- # [ emitted by buffer: 1 ]
         * 18:44:21.473 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # emitted by original Flux: 4
         * 18:44:21.775 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # emitted by original Flux: 5
         * 18:44:21.776 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- ** overflow & dropped: 5 **
         * 18:44:22.075 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # emitted by original Flux: 6
         * 18:44:22.076 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- ** overflow & dropped: 6 **
         * 18:44:22.295 [parallel-1] INFO com.kakao.example.ex8.Example8_5$Companion -- # onNext: 1
         * 18:44:22.296 [parallel-1] INFO com.kakao.example.ex8.Example8_5$Companion -- # [ emitted by buffer: 2 ]
         * 18:44:22.373 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # emitted by original Flux: 7
         * 18:44:22.674 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # emitted by original Flux: 8
         * 18:44:22.675 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- ** overflow & dropped: 8 **
         * 18:44:22.975 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- # emitted by original Flux: 9
         * 18:44:22.976 [parallel-2] INFO com.kakao.example.ex8.Example8_5$Companion -- ** overflow & dropped: 9 **
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.interval(Duration.ofMillis(300))
                .doOnNext { logger.info("# emitted by original Flux: $it") }
                .onBackpressureBuffer(
                    2,
                    { logger.info("** overflow & dropped: $it **") },
                    BufferOverflowStrategy.DROP_LATEST
                )
                .doOnNext { logger.info("# [ emitted by buffer: $it ]") }
                .publishOn(Schedulers.parallel(), false, 1)
                .subscribe(
                    {
                        try {
                            Thread.sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        logger.info("# onNext: $it")
                    },
                    { error -> logger.error("# onError: $error") },
                    { logger.info("# onComplete") }
                )

            Thread.sleep(3000)
        }
    }
}