package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import java.io.File

class Example14_8 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * using_example.txt:
         * Hello, world!
         * Nice to meet you!
         * Goodbye~!
         *
         * output:
         * [2024-10-01T21:49:53.561+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-01T21:49:53.571+09:00][ INFO] [main] Hello, world!
         * [2024-10-01T21:49:53.572+09:00][ INFO] [main] Nice to meet you!
         * [2024-10-01T21:49:53.572+09:00][ INFO] [main] Goodbye~!
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val path = this::class.java.classLoader.getResource("using_example.txt")!!.toURI()
            val file = File(path)

            Flux.using(
                { file.readLines().stream() }, // resource
                { Flux.fromStream(it) }, // Publisher
                { it.close() } // resource clean-up
            )
                .subscribe(log::info)
        }
    }
}
