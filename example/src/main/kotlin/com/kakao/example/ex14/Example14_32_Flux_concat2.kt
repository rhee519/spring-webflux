package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_32_Flux_concat2 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * concat(): 여러 개의 Flux를 순차적으로 실행
         *
         * output:
         * [2024-10-25T15:33:46.881+09:00] DEBUG [main] Using Slf4j logging framework
         * [2024-10-25T15:33:46.917+09:00] INFO  [main] [AstraZeneca,3000000]
         * [2024-10-25T15:33:46.917+09:00] INFO  [main] [Janssen,2000000]
         * [2024-10-25T15:33:46.917+09:00] INFO  [main] [Pfizer,1000000]
         * [2024-10-25T15:33:46.917+09:00] INFO  [main] [Moderna,4000000]
         * [2024-10-25T15:33:46.917+09:00] INFO  [main] [Novavax,2500000]
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.concat(
                Flux.fromIterable(getViralVector()),
                Flux.fromIterable(getMRNA()),
                Flux.fromIterable(getSubunit())
            )
                .subscribe { log.info("$it") }
        }

        private fun getViralVector() = SampleData.viralVectorVaccines
        private fun getMRNA() = SampleData.mRNAVaccines
        private fun getSubunit() = SampleData.subunitVaccines
    }
}
