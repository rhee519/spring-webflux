package com.kakao.example.ex10

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

@Slf4j
class Example10_5 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * publishOn(), subscribeOn()을 사용하지 않는 예제
         * 모든 Operator가 main thread에서 실행됨
         *
         * output:
         * [2024-09-21T15:59:03.960+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T15:59:03.967+09:00][ INFO] [main] # doOnNext fromIterable(): 1
         * [2024-09-21T15:59:03.967+09:00][ INFO] [main] # doOnNext fromIterable(): 3
         * [2024-09-21T15:59:03.967+09:00][ INFO] [main] # doOnNext fromIterable(): 5
         * [2024-09-21T15:59:03.967+09:00][ INFO] [main] # doOnNext filter(): 5
         * [2024-09-21T15:59:03.967+09:00][ INFO] [main] # doOnNext map(): 50
         * [2024-09-21T15:59:03.967+09:00][ INFO] [main] # onNext: 50
         * [2024-09-21T15:59:03.968+09:00][ INFO] [main] # doOnNext fromIterable(): 7
         * [2024-09-21T15:59:03.968+09:00][ INFO] [main] # doOnNext filter(): 7
         * [2024-09-21T15:59:03.968+09:00][ INFO] [main] # doOnNext map(): 70
         * [2024-09-21T15:59:03.968+09:00][ INFO] [main] # onNext: 70
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(listOf(1, 3, 5, 7))
                .doOnNext { logger.info("# doOnNext fromIterable(): $it") }
                .filter { it > 3 }
                .doOnNext { logger.info("# doOnNext filter(): $it") }
                .map { it * 10 }
                .doOnNext { logger.info("# doOnNext map(): $it") }
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}