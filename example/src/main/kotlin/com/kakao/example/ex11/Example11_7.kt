package com.kakao.example.ex11

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Slf4j
class Example11_7 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Inner Sequence 내부에서는 외부 Context 데이터를 읽을 수 있음
         * Inner Sequence 외부에서는 내부 Context 데이터를 읽을 수 없음
         *
         * output:
         * [2024-09-21T17:01:58.326+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T17:01:58.367+09:00][ INFO] [parallel-1] # onNext: [Apple] Steve, CEO
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val key1 = "company"

            Mono.just("Steve")
//                .transformDeferredContextual { mono, ctx -> Mono.just(ctx.get<String>("role")) } // reactor.core.Exceptions$ErrorCallbackNotImplemented: java.util.NoSuchElementException: Context does not contain key: role
                .flatMap { name ->
                    Mono.deferContextual { ctx ->
                        val company = ctx.get<String>(key1)
                        Mono.just("[$company] $name")
                            .transformDeferredContextual { mono, innerCtx ->
                                val role = innerCtx.get<String>("role")
                                mono.map { "$it, $role" }
                            }
                            .contextWrite { it.put("role", "CEO") } // innerCtx에만 적용됨
                    }
                }
                .publishOn(Schedulers.parallel())
                .contextWrite { it.put(key1, "Apple") }
                .subscribe { logger.info("# onNext: $it") }

            Thread.sleep(500)
        }
    }
}