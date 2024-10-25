package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class Example14_34_Flux_merge2 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * merge(): 여러 개의 Flux를 병렬로 실행
         *
         * output:
         * [2024-10-25T15:36:55.195+09:00] DEBUG [main] Using Slf4j logging framework
         * [2024-10-25T15:36:55.529+09:00] INFO  [parallel-1] 1
         * [2024-10-25T15:36:55.728+09:00] INFO  [parallel-2] 10001
         * [2024-10-25T15:36:55.835+09:00] INFO  [parallel-3] 2
         * [2024-10-25T15:36:56.140+09:00] INFO  [parallel-5] 3
         * [2024-10-25T15:36:56.229+09:00] INFO  [parallel-4] 10002
         * [2024-10-25T15:36:56.446+09:00] INFO  [parallel-6] 4
         * [2024-10-25T15:36:56.734+09:00] INFO  [parallel-7] 10003
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val usaStates = listOf(
                "Ohio",
                "Michigan",
                "New Jersey",
                "Illinois",
                "New Hampshire",
                "Virginia",
                "Vermont",
                "North Carolina",
                "Ontario",
                "Georgia",
            )
            Flux.merge(usaStates.map { getMeltDownRecoveryMsg(it) })
                .subscribe { log.info("# onNext: $it") }

            Thread.sleep(2000)
        }

        private fun getMeltDownRecoveryMsg(usaState: String): Mono<String> {
            return SampleData.nppMap[usaState] ?: Mono.empty()
        }
    }
}
