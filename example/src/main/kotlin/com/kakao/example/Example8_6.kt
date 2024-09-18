package com.kakao.example

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.BufferOverflowStrategy
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Slf4j
class Example8_6 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * BackPressure Buffer DROP_OLDEST Example
         * buffer overflow가 발생한 경우, oldest를 drop
         *
         * output:
         * 18:46:52.963 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # emitted by original Flux: 0
         * 18:46:52.966 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # [ emitted by buffer: 0 ]
         * 18:46:53.264 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # emitted by original Flux: 1
         * 18:46:53.561 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # emitted by original Flux: 2
         * 18:46:53.864 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # emitted by original Flux: 3
         * 18:46:53.865 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- ** overflow & dropped: 1 **
         * 18:46:53.968 [parallel-1] INFO com.kakao.example.Example8_6$Companion -- # onNext: 0
         * 18:46:53.968 [parallel-1] INFO com.kakao.example.Example8_6$Companion -- # [ emitted by buffer: 2 ]
         * 18:46:54.165 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # emitted by original Flux: 4
         * 18:46:54.460 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # emitted by original Flux: 5
         * 18:46:54.460 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- ** overflow & dropped: 3 **
         * 18:46:54.765 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # emitted by original Flux: 6
         * 18:46:54.766 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- ** overflow & dropped: 4 **
         * 18:46:54.973 [parallel-1] INFO com.kakao.example.Example8_6$Companion -- # onNext: 2
         * 18:46:54.973 [parallel-1] INFO com.kakao.example.Example8_6$Companion -- # [ emitted by buffer: 5 ]
         * 18:46:55.061 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # emitted by original Flux: 7
         * 18:46:55.365 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # emitted by original Flux: 8
         * 18:46:55.366 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- ** overflow & dropped: 6 **
         * 18:46:55.664 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- # emitted by original Flux: 9
         * 18:46:55.664 [parallel-2] INFO com.kakao.example.Example8_6$Companion -- ** overflow & dropped: 7 **
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.interval(Duration.ofMillis(300))
                .doOnNext { logger.info("# emitted by original Flux: $it") }
                .onBackpressureBuffer(
                    2,
                    { logger.info("** overflow & dropped: $it **") },
                    BufferOverflowStrategy.DROP_OLDEST
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