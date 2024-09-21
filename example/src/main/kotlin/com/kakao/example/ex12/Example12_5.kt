package com.kakao.example.ex12

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example12_5 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * Debugging Example (checkpoint() 4)
         * - checkpoint with description and traceback
         *
         * output:
         * [2024-09-21T21:37:20.674+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T21:37:20.681+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:37:20.682+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:37:20.682+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:37:20.685+09:00][ERROR] [main] # onError:
         * java.lang.ArithmeticException: / by zero
         * 	at com.kakao.example.ex12.Example12_5$Companion$main$1.invoke(Example12_5.kt:19)
         * 	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException:
         * Assembly trace from producer [reactor.core.publisher.FluxZip], described as [zipWith] :
         * 	reactor.core.publisher.Flux.checkpoint(Flux.java:3687)
         * 	com.kakao.example.ex12.Example12_5$Companion.main(Example12_5.kt:20)
         * Error has been observed at the following site(s):
         * 	*__checkpoint(zipWith) ⇢ at com.kakao.example.ex12.Example12_5$Companion.main(Example12_5.kt:20)
         * 	|_     checkpoint(map) ⇢ at com.kakao.example.ex12.Example12_5$Companion.main(Example12_5.kt:22)
         * Original Stack Trace:
         * 		at com.kakao.example.ex12.Example12_5$Companion$main$1.invoke(Example12_5.kt:19)
         * 		...
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0)) { a, b -> a / b }
                .checkpoint("zipWith", true)
                .map { it + 2 }
                .checkpoint("map", true)
                .subscribe(
                    { log.info("# onNext: $it") },
                    { error -> log.error("# onError: ", error) },
                )

            Thread.sleep(100)
        }
    }
}