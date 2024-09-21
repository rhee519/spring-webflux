package com.kakao.example.ex11

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Slf4j
class Example11_1 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Context: Reactor component 간에 전파되는 key-value store
         * downstream -> upstream으로 Context가 전파되어 Operator 체인의 각 Operator가 해당 Context를 사용할 수 있음
         * 구독이 발생할 때마다 해당 구독과 연결된 하나의 Context가 생성됨
         * Context는 thread-safe하여 다수의 thread에서 동시에 접근해도 안전함
         *
         * output:
         * [2024-09-21T16:36:13.546+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T16:36:13.561+09:00][ INFO] [boundedElastic-1] # just doOnNext: Hello Steve
         * [2024-09-21T16:36:13.562+09:00][ INFO] [parallel-1] # onNext: Hello Steve jobs
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Mono.deferContextual { ctx -> // ctx: ContextView (read-only)
                val firstName = ctx.get<String>("firstName")
                Mono.just("Hello $firstName")
                    .doOnNext { logger.info("# just doOnNext: $it") }
            }
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .transformDeferredContextual { mono, ctx ->
                    val lastName = ctx.get<String>("lastName")
                    mono.map { "$it $lastName" }
                }
                .contextWrite { it.put("lastName", "jobs") }
                .contextWrite { it.put("firstName", "Steve") }
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}