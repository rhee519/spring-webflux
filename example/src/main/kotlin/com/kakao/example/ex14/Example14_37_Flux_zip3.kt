package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class Example14_37_Flux_zip3 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /**
         * ```
         * output:
         * [2024-10-25T16:16:13.023+09:00] DEBUG [main] Using Slf4j logging framework
         * [2024-10-25T16:16:13.061+09:00] INFO  [main] # onNext: t=10, sum=25
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=11, sum=29
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=12, sum=35
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=13, sum=23
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=14, sum=22
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=15, sum=29
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=16, sum=43
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=17, sum=15
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=18, sum=17
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=19, sum=16
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=20, sum=8
         * [2024-10-25T16:16:13.062+09:00] INFO  [main] # onNext: t=21, sum=16
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val infectedPeoplePerHour = getInfectedPeoplePerHour(10, 21)
            infectedPeoplePerHour.subscribe {
                val t = it.t1.t1
                val sum = it.t1.t2 + it.t2.t2 + it.t3.t2
                log.info("# onNext: t=$t, sum=$sum")
            }

            Thread.sleep(2500)
        }

        private fun getInfectedPeoplePerHour(start: Int, end: Int) = run {
            Flux.zip(
                Flux.fromIterable(SampleData.seoulInfected).filter { it.t1 in start..end },
                Flux.fromIterable(SampleData.incheonInfected).filter { it.t1 in start..end },
                Flux.fromIterable(SampleData.suwonInfected).filter { it.t1 in start..end },
            )
        }
    }
}
