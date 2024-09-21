package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Slf4j
class Example10_2 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * publishOn() Example
         *
         * publishOn() 기준으로 downstream의 실행 thread를 지정함
         *
         * output:
         * [2024-09-21T15:49:05.929+09:00][ INFO] [main] # doOnSubscribe: reactor.core.publisher.FluxPeekFuseable$PeekFuseableSubscriber@185a6e9
         * [2024-09-21T15:49:05.931+09:00][ INFO] [main] # doOnNext: 1
         * [2024-09-21T15:49:05.931+09:00][ INFO] [main] # doOnNext: 3
         * [2024-09-21T15:49:05.931+09:00][ INFO] [main] # doOnNext: 5
         * [2024-09-21T15:49:05.931+09:00][ INFO] [main] # doOnNext: 7
         * [2024-09-21T15:49:05.931+09:00][ INFO] [parallel-1] # onNext: 1
         * [2024-09-21T15:49:05.931+09:00][ INFO] [parallel-1] # onNext: 3
         * [2024-09-21T15:49:05.931+09:00][ INFO] [parallel-1] # onNext: 5
         * [2024-09-21T15:49:05.931+09:00][ INFO] [parallel-1] # onNext: 7
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(listOf(1, 3, 5, 7))
                .doOnNext { logger.info("# doOnNext: $it") }
                .doOnSubscribe { logger.info("# doOnSubscribe: $it") }
                .publishOn(Schedulers.parallel())
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}