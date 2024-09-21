package com.kakao.example.ex11

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.util.context.Context

@Slf4j
class Example11_3 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Context API example
         *
         * output:
         * [2024-09-21T16:44:23.092+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T16:44:23.103+09:00][ INFO] [parallel-1] # onNext: [Apple] Steve Jobs
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val key1 = "company"
            val key2 = "firstName"
            val key3 = "lastName"

            Mono.deferContextual { ctx ->
                val company = ctx.get<String>(key1)
                val firstName = ctx.get<String>(key2)
                val lastName = ctx.get<String>(key3)
                Mono.just("[$company] $firstName $lastName")
            }
                .publishOn(Schedulers.parallel())
                .contextWrite { it.putAll(Context.of(key2, "Steve", key3, "Jobs").readOnly()) }
                .contextWrite { it.put(key1, "Apple") }
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}