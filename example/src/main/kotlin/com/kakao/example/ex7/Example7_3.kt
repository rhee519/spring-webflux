package com.kakao.example.ex7

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Slf4j
class Example7_3 {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private val webClient = WebClient.create()
        private val objectMapper = ObjectMapper()

        /**
         * output:
         * Cold Sequence Example
         * 17:53:43.409 [reactor-http-nio-2] INFO com.kakao.example.ex7.Example7_3$Companion -- # datetime 1: 2024-09-18T17:53:43.273209+09:00
         * 17:53:45.252 [reactor-http-nio-2] INFO com.kakao.example.ex7.Example7_3$Companion -- # datetime 2: 2024-09-18T17:53:45.143743+09:00
         * ----------
         * Hot Sequence Example
         * 17:53:47.267 [reactor-http-nio-2] INFO com.kakao.example.ex7.Example7_3$Companion -- # datetime 1: 2024-09-18T17:53:47.157558+09:00
         * 17:53:49.099 [main] INFO com.kakao.example.ex7.Example7_3$Companion -- # datetime 2: 2024-09-18T17:53:47.157558+09:00
         */
        @JvmStatic
        fun main(args: Array<String>) {
            println("Cold Sequence Example")
            val mono = getWorldTime(url = "http://worldtimeapi.org/api/timezone/Asia/Seoul")
            mono.subscribe { logger.info("# datetime 1: $it") }
            Thread.sleep(2000)
            mono.subscribe { logger.info("# datetime 2: $it") }
            Thread.sleep(2000)

            println("----------")
            println("Hot Sequence Example")
            val hotMono = getWorldTime(url = "http://worldtimeapi.org/api/timezone/Asia/Seoul").cache()
            hotMono.subscribe { logger.info("# datetime 1: $it") }
            Thread.sleep(2000)
            hotMono.subscribe { logger.info("# datetime 2: $it") }
            Thread.sleep(2000)
        }

        private fun getWorldTime(url: String): Mono<String> {
            return webClient.get().uri(url).retrieve().bodyToMono(String::class.java).map {
                val body = objectMapper.readTree(it)
                val dateTime = body["datetime"].asText()
                dateTime
            }
        }
    }
}
