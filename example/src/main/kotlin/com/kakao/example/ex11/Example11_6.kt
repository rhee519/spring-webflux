package com.kakao.example.ex11

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Slf4j
class Example11_6 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Context는 downstream에서 upstream으로 전파됨
         *
         * output:
         * [2024-09-21T16:53:57.538+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T16:53:57.552+09:00][ INFO] [parallel-1] # onNext: [Apple] Steve
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val key1 = "company"
            val key2 = "name"

            Mono.deferContextual { ctx ->
                val company = ctx.get<String>(key1)
                Mono.just(company)
            }
                .publishOn(Schedulers.parallel())
                .contextWrite { it.put(key2, "Bill") } // 이 context는 downstream으로 전파되지 않음
                .transformDeferredContextual { mono, ctx ->
                    val name = ctx.getOrDefault(key2, "Steve") // "Steve"
                    mono.map { "[$it] $name" }
                }
                .contextWrite { it.put(key1, "Apple") }
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}