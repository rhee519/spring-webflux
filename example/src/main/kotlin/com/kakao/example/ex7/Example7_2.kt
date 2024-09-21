package com.kakao.example.ex7

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import java.time.Duration

@Slf4j
class Example7_2 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * Hot Sequence(Publisher) Example
         * - Hot Sequence는 Subscriber가 subscribe할 때마다 새로운 데이터를 생성하지 않고, 이미 생성된 데이터를 공유하는 Publisher
         * - Hot Sequence는 Subscriber 간에 데이터를 공유함
         * - delayElements() default scheduler 설정(parallel)에 의해 emit되는 데이터별로 다른 thread에서 실행됨
         *
         * output:
         * 17:42:00.747 [main] INFO com.kakao.example.ex7.Example7_2$Companion -- Concert begins!
         * ----------
         * 17:42:01.816 [parallel-1] INFO com.kakao.example.ex7.Example7_2$Companion -- # Subscriber 1 is watching: Justin Bieber
         * 17:42:02.819 [parallel-2] INFO com.kakao.example.ex7.Example7_2$Companion -- # Subscriber 1 is watching: Ariana Grande
         * 17:42:03.824 [parallel-3] INFO com.kakao.example.ex7.Example7_2$Companion -- # Subscriber 1 is watching: BTS
         * 17:42:03.825 [parallel-3] INFO com.kakao.example.ex7.Example7_2$Companion -- # Subscriber 2 is watching: BTS
         * 17:42:04.830 [parallel-4] INFO com.kakao.example.ex7.Example7_2$Companion -- # Subscriber 1 is watching: Taylor Swift
         * 17:42:04.830 [parallel-4] INFO com.kakao.example.ex7.Example7_2$Companion -- # Subscriber 2 is watching: Taylor Swift
         * 17:42:05.832 [parallel-5] INFO com.kakao.example.ex7.Example7_2$Companion -- # Subscriber 1 is watching: Lady Gaga
         * 17:42:05.832 [parallel-5] INFO com.kakao.example.ex7.Example7_2$Companion -- # Subscriber 2 is watching: Lady Gaga
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val artists = listOf("Justin Bieber", "Ariana Grande", "BTS", "Taylor Swift", "Lady Gaga")
            logger.info("Concert begins!")

            val concertFlux = Flux.fromIterable(artists)
                .delayElements(Duration.ofSeconds(1))
                .share()

            concertFlux.subscribe {
                logger.info("# Subscriber 1 is watching: $it")
            }

            println("----------")

            Thread.sleep(2500)
            concertFlux.subscribe {
                logger.info("# Subscriber 2 is watching: $it")
            }

            Thread.sleep(3000)
        }
    }
}
