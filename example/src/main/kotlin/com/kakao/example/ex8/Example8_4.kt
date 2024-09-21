package com.kakao.example.ex8

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Slf4j
class Example8_4 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * BackPressure Latest Example
         * buffer가 가득 찬 경우, 가장 마지막에 emit 된 데이터만 남김
         *
         * output:
         * 18:34:58.916 [parallel-1] INFO com.kakao.example.ex8.Example8_4$Companion -- # onNext: 0
         * 18:34:58.924 [parallel-1] INFO com.kakao.example.ex8.Example8_4$Companion -- # onNext: 1
         * ...
         * 18:35:00.455 [parallel-1] INFO com.kakao.example.ex8.Example8_4$Companion -- # onNext: 254
         * 18:35:00.462 [parallel-1] INFO com.kakao.example.ex8.Example8_4$Companion -- # onNext: 255
         * 18:35:00.468 [parallel-1] INFO com.kakao.example.ex8.Example8_4$Companion -- # onNext: 1161
         * 18:35:00.475 [parallel-1] INFO com.kakao.example.ex8.Example8_4$Companion -- # onNext: 1162
         * ...
         * 18:35:00.907 [parallel-1] INFO com.kakao.example.ex8.Example8_4$Companion -- # onNext: 1232
         * 18:35:00.913 [parallel-1] INFO com.kakao.example.ex8.Example8_4$Companion -- # onNext: 1233
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.interval(Duration.ofMillis(1))
                .onBackpressureLatest()
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