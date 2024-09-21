package com.kakao.example.ex12

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example12_6 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * Debugging Example (checkpoint() 4)
         * - checkpoints within multiple Operators
         *
         * output:
         * [2024-09-21T21:43:59.859+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-09-21T21:43:59.868+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:43:59.868+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:43:59.868+09:00][ INFO] [main] # onNext: 4
         * [2024-09-21T21:43:59.871+09:00][ERROR] [main] # onError:
         * java.lang.ArithmeticException: / by zero
         * 	at com.kakao.example.ex12.Example12_6$Companion$divide$1.invoke(Example12_6.kt:34)
         * 	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException:
         * Assembly trace from producer [reactor.core.publisher.FluxZip] :
         * 	reactor.core.publisher.Flux.checkpoint(Flux.java:3622)
         * 	com.kakao.example.ex12.Example12_6$Companion.main(Example12_6.kt:22)
         * Error has been observed at the following site(s):
         * 	*__checkpoint() ⇢ at com.kakao.example.ex12.Example12_6$Companion.main(Example12_6.kt:22)
         * 	|_ checkpoint() ⇢ at com.kakao.example.ex12.Example12_6$Companion.main(Example12_6.kt:23)
         * Original Stack Trace:
         * 		at com.kakao.example.ex12.Example12_6$Companion$divide$1.invoke(Example12_6.kt:34)
         * 		...
         * 	```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val source = Flux.just(2, 4, 6, 8)
            val other = Flux.just(1, 2, 3, 0)
            val divideSource = divide(source, other).checkpoint()
            val plusSource = plus(divideSource, 2).checkpoint()

            plusSource.subscribe(
                { log.info("# onNext: $it") },
                { error -> log.error("# onError: ", error) },
            )

            Thread.sleep(100)
        }

        private fun divide(source: Flux<Int>, other: Flux<Int>): Flux<Int> {
            return source.zipWith(other) { a, b -> a / b }
        }

        private fun plus(source: Flux<Int>, addend: Int): Flux<Int> {
            return source.map { it + addend }
        }
    }
}