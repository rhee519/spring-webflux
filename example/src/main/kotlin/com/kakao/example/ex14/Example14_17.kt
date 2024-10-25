package com.kakao.example.ex14

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class Example14_17 {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private val vaccineMap = SampleData.getCovidVaccines()

        /**
         * ```
         * filterWhen(): filter()와 비슷하지만, 조건을 Mono<Boolean>으로 받아서 조건에 맞는 데이터만 통과시킨다.
         *
         * output:
         * [2024-10-25T14:32:21.174+09:00][DEBUG] [main] Using Slf4j logging framework
         * [2024-10-25T14:32:21.209+09:00][ INFO] [parallel-2] # onNext: AstraZeneca
         * [2024-10-25T14:32:21.209+09:00][ INFO] [parallel-3] # onNext: Moderna
         * ```
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.fromIterable(SampleData.coronaVaccineNames)
                .filterWhen { vaccineName ->
                    val vaccineCount = vaccineMap[vaccineName]?.t2
                    Mono.just(vaccineCount?.let { it >= 3_000_000 } ?: false)
                        .publishOn(Schedulers.parallel())
                }
                .subscribe { log.info("# onNext: $it") }

            Thread.sleep(1000)
        }
    }
}
