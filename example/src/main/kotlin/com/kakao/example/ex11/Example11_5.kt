package com.kakao.example.ex11

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Slf4j
class Example11_5 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Context는 Subscription 별로 생성됨
         *
         * output:
         * [2024-09-21T16:49:34.588+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T16:49:34.599+09:00][ INFO] [parallel-1] # Subscriber1 onNext: Company: [Apple]
         * [2024-09-21T16:49:34.599+09:00][ INFO] [parallel-2] # Subscriber2 onNext: Company: [Microsoft]
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val key1 = "company"

            val mono = Mono.deferContextual { ctx ->
                val company = ctx.get<String>(key1)
                Mono.just("Company: [$company]")
            }
                .publishOn(Schedulers.parallel()) // parallel 이므로 Subscriber1, Subscriber2가 별도의 스레드에서 병렬로 실행됨

            mono.contextWrite { it.put(key1, "Apple") }
                .subscribe { logger.info("# Subscriber1 onNext: $it") }
            mono.contextWrite { it.put(key1, "Microsoft") }
                .subscribe { logger.info("# Subscriber2 onNext: $it") }

            Thread.sleep(500)
        }
    }
}