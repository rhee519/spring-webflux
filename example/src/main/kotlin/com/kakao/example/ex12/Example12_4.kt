package com.kakao.example.ex12

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example12_4 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * Debugging Example (checkpoint() 3)
         * - checkpoint with description
         *
         * output:
         * [2024-09-21T21:35:05.387+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T21:35:05.395+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:35:05.395+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:35:05.396+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:35:05.398+09:00][ERROR] [main] # onError:
         * java.lang.ArithmeticException: / by zero
         * 	at com.kakao.example.ex12.Example12_4$Companion$main$1.invoke(Example12_4.kt:19)
         * 	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException:
         * Error has been observed at the following site(s):
         * 	*__checkpoint ⇢ zipWith
         * 	|_ checkpoint ⇢ map
         * Original Stack Trace:
         * 		at com.kakao.example.ex12.Example12_4$Companion$main$1.invoke(Example12_4.kt:19)
         * 		...
         * 	```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0)) { a, b -> a / b }
                .checkpoint("zipWith")
                .map { it + 2 }
                .checkpoint("map")
                .subscribe(
                    { log.info("# onNext: $it") },
                    { error -> log.error("# onError: ", error) },
                )

            Thread.sleep(100)
        }
    }
}