package com.kakao.example.ex8

import lombok.extern.slf4j.Slf4j
import org.reactivestreams.Subscription
import org.slf4j.LoggerFactory
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux

@Slf4j
class Example8_1 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        /**
         * output:
         * 18:15:01.730 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # hookOnSubscribe(최초 요청 데이터 개수 설정): 1
         * 18:15:01.732 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # doOnRequest(subscribe 요청 데이터 개수): 1
         * 18:15:03.747 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # hookOnNext(요청한 데이터): 1
         * 18:15:03.748 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # doOnRequest(subscribe 요청 데이터 개수): 1
         * 18:15:05.753 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # hookOnNext(요청한 데이터): 2
         * 18:15:05.754 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # doOnRequest(subscribe 요청 데이터 개수): 1
         * 18:15:07.756 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # hookOnNext(요청한 데이터): 3
         * 18:15:07.756 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # doOnRequest(subscribe 요청 데이터 개수): 1
         * 18:15:09.762 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # hookOnNext(요청한 데이터): 4
         * 18:15:09.762 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # doOnRequest(subscribe 요청 데이터 개수): 1
         * 18:15:11.768 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # hookOnNext(요청한 데이터): 5
         * 18:15:11.768 [main] INFO com.kakao.example.ex8.Example8_1$Companion -- # doOnRequest(subscribe 요청 데이터 개수): 1
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Flux.range(1, 5)
                .doOnRequest {
                    logger.info("# doOnRequest(subscribe 요청 데이터 개수): $it")
                }
                .subscribe(object : BaseSubscriber<Int>() {
                    override fun hookOnSubscribe(subscription: Subscription) {
                        logger.info("# hookOnSubscribe(최초 요청 데이터 개수 설정): 1")
                        request(1)
                    }

                    override fun hookOnNext(value: Int) {
                        Thread.sleep(2000)
                        logger.info("# hookOnNext(요청한 데이터): $value")
                        request(1)
                    }
                })
        }
    }
}