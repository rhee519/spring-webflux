package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.util.function.Tuples

class Example14_10 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-20T23:16:29.552+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-20T23:16:29.559+09:00][ INFO] [main] # onNext: 3 * 1 = 3
         * [2024-10-20T23:16:29.559+09:00][ INFO] [main] # onNext: 3 * 2 = 6
         * [2024-10-20T23:16:29.559+09:00][ INFO] [main] # onNext: 3 * 3 = 9
         * [2024-10-20T23:16:29.559+09:00][ INFO] [main] # onNext: 3 * 4 = 12
         * [2024-10-20T23:16:29.560+09:00][ INFO] [main] # onNext: 3 * 5 = 15
         * [2024-10-20T23:16:29.560+09:00][ INFO] [main] # onNext: 3 * 6 = 18
         * [2024-10-20T23:16:29.560+09:00][ INFO] [main] # onNext: 3 * 7 = 21
         * [2024-10-20T23:16:29.560+09:00][ INFO] [main] # onNext: 3 * 8 = 24
         * [2024-10-20T23:16:29.560+09:00][ INFO] [main] # onNext: 3 * 9 = 27
         * [2024-10-20T23:16:29.560+09:00][ INFO] [main] # 구구단 3단 종료!
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val dan = 3
            Flux.generate(
                { Tuples.of(dan, 1) }, // 초기 state
                { state, sink ->
                    sink.next("${state.t1} * ${state.t2} = ${state.t1 * state.t2}")
                    if (state.t2 == 9)
                        sink.complete()
                    Tuples.of(state.t1, state.t2 + 1)
                }, // generator
                { state -> log.info("# 구구단 ${state.t1}단 종료!") } // 상태 전이 시 호출
            ).subscribe {
                log.info("# onNext: $it")
            }
        }
    }
}
