package com.kakao.example.ex12

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example12_3 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * Debugging Example (checkpoint() 2)
         *
         * output:
         * [2024-09-21T21:32:24.926+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T21:32:24.936+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:32:24.936+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:32:24.936+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:32:24.944+09:00][ERROR] [main] # onError:
         * java.lang.ArithmeticException: / by zero
         * 	at com.kakao.example.ex12.Example12_3$Companion$main$1.invoke(Example12_3.kt:34)
         * 	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException:
         * Assembly trace from producer [reactor.core.publisher.FluxZip] :
         * 	reactor.core.publisher.Flux.checkpoint(Flux.java:3622)
         * 	com.kakao.example.ex12.Example12_3$Companion.main(Example12_3.kt:35)
         * Error has been observed at the following site(s):
         * 	*__checkpoint() ⇢ at com.kakao.example.ex12.Example12_3$Companion.main(Example12_3.kt:35)
         * 	|_ checkpoint() ⇢ at com.kakao.example.ex12.Example12_3$Companion.main(Example12_3.kt:37)
         * Original Stack Trace:
         * 		at com.kakao.example.ex12.Example12_3$Companion$main$1.invoke(Example12_3.kt:34)
         * 		...
         * 	```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0)) { a, b -> a / b }
                .checkpoint()
                .map { it + 2 }
                .checkpoint()
                .subscribe(
                    { log.info("# onNext: $it") },
                    { error -> log.error("# onError: ", error) },
                )

            Thread.sleep(100)
        }
    }
}