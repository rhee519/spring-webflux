package com.kakao.example.ex8

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Slf4j
class Example8_2 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * BackPressure Error Example
         * buffer가 가득 찬 경우 error 발생하고 종료
         *
         * output:
         * 18:20:47.964 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 0
         * 18:20:47.965 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 1
         * 18:20:47.966 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 2
         * 18:20:47.966 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 3
         * 18:20:47.967 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 4
         * 18:20:47.968 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 5
         * 18:20:47.969 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 6
         * 18:20:47.970 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 7
         * 18:20:47.971 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 8
         * 18:20:47.972 [parallel-1] INFO com.kakao.example.ex8.Example8_2$Companion -- # onNext: 0
         * 18:20:47.972 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 9
         * 18:20:47.973 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 10
         * 18:20:47.974 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 11
         * 18:20:47.975 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 12
         * 18:20:47.976 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 13
         * 18:20:47.977 [parallel-2] INFO com.kakao.example.ex8.Example8_2$Companion -- # doOnNext: 14
         * 18:20:47.978 [parallel-1] INFO com.kakao.example.ex8.Example8_2$Companion -- # onNext: 1
         * ...
         * 18:20:49.536 [parallel-1] INFO com.kakao.example.ex8.Example8_2$Companion -- # onNext: 255
         * 18:20:49.537 [parallel-1] ERROR com.kakao.example.ex8.Example8_2$Companion -- # onError: reactor.core.Exceptions$OverflowException: The receiver is overrun by more signals than expected (bounded queue...)
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.interval(Duration.ofMillis(1))
                .onBackpressureError()
                .doOnNext { logger.info("# doOnNext: $it") }
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