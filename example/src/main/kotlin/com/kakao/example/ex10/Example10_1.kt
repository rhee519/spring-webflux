package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Slf4j
class Example10_1 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * subscribeOn() Example
         *
         * subscribeOn(): 원본 Publisher의 동작을 처리할 thread를 할당
         * doOnNext(): 원본 Flux에서 emit된 데이터를 핸들링 (예제에서는 로깅)
         * doOnSubscribe(): 구독이 시작될 때 추가적인 로직 처리가 필요한 경우 사용 -> main thread에서 실행됨
         *
         * output:
         * [2024-09-21T15:44:08.784+09:00][ INFO] [main] # doOnSubscribe: reactor.core.publisher.FluxPeek$PeekSubscriber@4116aac9
         * [2024-09-21T15:44:08.786+09:00][ INFO] [boundedElastic-1] # doOnNext: 1
         * [2024-09-21T15:44:08.786+09:00][ INFO] [boundedElastic-1] # onNext: 1
         * [2024-09-21T15:44:08.786+09:00][ INFO] [boundedElastic-1] # doOnNext: 3
         * [2024-09-21T15:44:08.786+09:00][ INFO] [boundedElastic-1] # onNext: 3
         * [2024-09-21T15:44:08.786+09:00][ INFO] [boundedElastic-1] # doOnNext: 5
         * [2024-09-21T15:44:08.786+09:00][ INFO] [boundedElastic-1] # onNext: 5
         * [2024-09-21T15:44:08.786+09:00][ INFO] [boundedElastic-1] # doOnNext: 7
         * [2024-09-21T15:44:08.786+09:00][ INFO] [boundedElastic-1] # onNext: 7
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(listOf(1, 3, 5, 7))
                .subscribeOn(Schedulers.boundedElastic()) // 지정하지 않으면 main thread에서 subscribe됨
                .doOnNext { logger.info("# doOnNext: $it") }
                .doOnSubscribe { logger.info("# doOnSubscribe: $it") }
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}