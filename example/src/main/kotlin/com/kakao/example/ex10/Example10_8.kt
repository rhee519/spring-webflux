package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Slf4j
class Example10_8 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * subscribeOn(), publishOn()을 함께 사용하는 예제
         * subscribeOn()을 사용하여 thread를 지정하면, 구독이 발생한 직후부터 적용됨
         * 두 Operator를 함께 사용하여 원본 Publisher에서 데이터를 emit하는 thread와 emit된 데이터를 가공하는 thread를 적절히 분리할 수 있음
         *
         * output:
         * [2024-09-21T16:06:40.958+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T16:06:40.989+09:00][ INFO] [boundedElastic-1] # doOnNext fromIterable(): 1
         * [2024-09-21T16:06:40.989+09:00][ INFO] [boundedElastic-1] # doOnNext fromIterable(): 3
         * [2024-09-21T16:06:40.989+09:00][ INFO] [boundedElastic-1] # doOnNext fromIterable(): 5
         * [2024-09-21T16:06:40.989+09:00][ INFO] [boundedElastic-1] # doOnNext filter(): 5
         * [2024-09-21T16:06:40.989+09:00][ INFO] [boundedElastic-1] # doOnNext fromIterable(): 7
         * [2024-09-21T16:06:40.989+09:00][ INFO] [boundedElastic-1] # doOnNext filter(): 7
         * [2024-09-21T16:06:40.990+09:00][ INFO] [parallel-1] # doOnNext map(): 50
         * [2024-09-21T16:06:40.990+09:00][ INFO] [parallel-1] # onNext: 50
         * [2024-09-21T16:06:40.990+09:00][ INFO] [parallel-1] # doOnNext map(): 70
         * [2024-09-21T16:06:40.990+09:00][ INFO] [parallel-1] # onNext: 70
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(listOf(1, 3, 5, 7))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext { logger.info("# doOnNext fromIterable(): $it") }
                .filter { it > 3 }
                .doOnNext { logger.info("# doOnNext filter(): $it") }
                .publishOn(Schedulers.parallel())
                .map { it * 10 }
                .doOnNext { logger.info("# doOnNext map(): $it") }
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}