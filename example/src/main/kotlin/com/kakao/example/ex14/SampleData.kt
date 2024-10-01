package com.kakao.example.ex14

import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import reactor.util.function.Tuples
import java.time.Duration
import java.util.stream.Collectors

object SampleData {
    val coinNames: List<String> = listOf("BTC", "ETH", "XRP", "ICX", "EOS", "BCH")
    val btcPrices: List<Int> = listOf(50000000, 50100000, 50700000, 51500000, 52000000)
    val coins: List<Tuple2<String, Int>> = listOf(
        Tuples.of("BTC", 52000000),
        Tuples.of("ETH", 1720000),
        Tuples.of("XRP", 533),
        Tuples.of("ICX", 2080),
        Tuples.of("EOS", 4020),
        Tuples.of("BCH", 558000)
    )
    val btcTopPricesPerYear: List<Tuple2<Int, Long>> = listOf(
        Tuples.of(2010, 565L),
        Tuples.of(2011, 36094L),
        Tuples.of(2012, 17425L),
        Tuples.of(2013, 1405209L),
        Tuples.of(2014, 1237182L),
        Tuples.of(2015, 557603L),
        Tuples.of(2016, 1111811L),
        Tuples.of(2017, 22483583L),
        Tuples.of(2018, 19521543L),
        Tuples.of(2019, 15761568L),
        Tuples.of(2020, 22439002L),
        Tuples.of(2021, 63364000L)
    )
    val coronaVaccineNames: List<CovidVaccine> = CovidVaccine.toList()
    val coronaVaccines: List<Tuple2<CovidVaccine, Int>> = listOf(
        Tuples.of(CovidVaccine.Pfizer, 1000000),
        Tuples.of(CovidVaccine.AstraZeneca, 3000000),
        Tuples.of(CovidVaccine.Moderna, 4000000),
        Tuples.of(CovidVaccine.Janssen, 2000000),
        Tuples.of(CovidVaccine.Novavax, 2500000)
    )
    val viralVectorVaccines: List<Tuple2<CovidVaccine, Int>> = listOf(
        Tuples.of(CovidVaccine.AstraZeneca, 3000000),
        Tuples.of(CovidVaccine.Janssen, 2000000)
    )
    val mRNAVaccines: List<Tuple2<CovidVaccine, Int>> = listOf(
        Tuples.of(CovidVaccine.Pfizer, 1000000),
        Tuples.of(CovidVaccine.Moderna, 4000000)
    )
    val subunitVaccines: List<Tuple2<CovidVaccine, Int>> = listOf(
        Tuples.of(CovidVaccine.Novavax, 2500000)
    )
    val btcTopPricesPerYearMap: () -> Map<Int, Tuple2<Int, Long>> = {
        btcTopPricesPerYear
            .stream()
            .collect(
                Collectors.toMap(
                    { key -> key.t1 },
                    { value -> value }
                )
            )
    }
    val covidVaccines: () -> Map<CovidVaccine, Tuple2<CovidVaccine, Int>> = {
        coronaVaccines
            .stream()
            .collect(
                Collectors.toMap(
                    { key -> key.t1 },
                    { value -> value }
                )
            )
    }

    var nppMap: MutableMap<String, Mono<String>> = HashMap()
    var morseCodeMap: MutableMap<String, String> = HashMap()
    var morseCodes: Array<String> = arrayOf(
        ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-",
        ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-",
        ".--", "-..-", "-.--", "--.."
    )

    init {
        nppMap["Ontario"] =
            Mono.just("Ontario Done")
                .delayElement(Duration.ofMillis(1500L))
        nppMap["Vermont"] =
            Mono.just("Vermont Done")
                .delayElement(Duration.ofMillis(400L))
        nppMap["New Hampshire"] = Mono.just("New Hampshire Done")
            .delayElement(Duration.ofMillis(700L))
        nppMap["New Jersey"] = Mono.just("New Jersey Done")
            .delayElement(Duration.ofMillis(500L))
        nppMap["Ohio"] =
            Mono.just("Ohio Done")
                .delayElement(Duration.ofMillis(1000L))
        nppMap["Michigan"] =
            Mono.just("Michigan Done")
                .delayElement(Duration.ofMillis(200L))
        nppMap["Illinois"] =
            Mono.just("Illinois Done")
                .delayElement(Duration.ofMillis(300L))
        nppMap["Virginia"] =
            Mono.just("Virginia Done")
                .delayElement(Duration.ofMillis(600L))
        nppMap["North Carolina"] = Mono.just("North Carolina Done")
            .delayElement(Duration.ofMillis(800L))
        nppMap["Georgia"] =
            Mono.just("Georgia Done")
                .delayElement(Duration.ofMillis(900L))

        for (c in 'a'.code..('a'.code + 25)) {
            morseCodeMap[morseCodes[c - ('z'.code - 25)]] = c.toString()
        }
    }

    val seoulInfected: List<Tuple2<Int, Int>> = listOf(
        Tuples.of(1, 0), Tuples.of(2, 0), Tuples.of(3, 0), Tuples.of(4, 0),
        Tuples.of(5, 0), Tuples.of(6, 0), Tuples.of(7, 0), Tuples.of(8, 0),
        Tuples.of(9, 0), Tuples.of(10, 20), Tuples.of(11, 23),
        Tuples.of(12, 33), Tuples.of(13, 10), Tuples.of(14, 15),
        Tuples.of(15, 20), Tuples.of(16, 30), Tuples.of(17, 10),
        Tuples.of(18, 11), Tuples.of(19, 13), Tuples.of(20, 8),
        Tuples.of(21, 14), Tuples.of(22, 4), Tuples.of(23, 7), Tuples.of(24, 2)
    )

    val incheonInfected: List<Tuple2<Int, Int>> = listOf(
        Tuples.of(1, 0), Tuples.of(2, 0), Tuples.of(3, 0), Tuples.of(4, 0),
        Tuples.of(5, 0), Tuples.of(6, 0), Tuples.of(7, 0), Tuples.of(8, 0),
        Tuples.of(9, 0), Tuples.of(10, 3), Tuples.of(11, 5), Tuples.of(12, 2),
        Tuples.of(13, 10), Tuples.of(14, 5), Tuples.of(15, 6),
        Tuples.of(16, 7), Tuples.of(17, 2), Tuples.of(18, 5),
        Tuples.of(19, 2), Tuples.of(20, 0), Tuples.of(21, 2),
        Tuples.of(22, 0), Tuples.of(23, 2), Tuples.of(24, 1)
    )

    val suwonInfected: List<Tuple2<Int, Int>> = listOf(
        Tuples.of(1, 0), Tuples.of(2, 0), Tuples.of(3, 0), Tuples.of(4, 0),
        Tuples.of(5, 0), Tuples.of(6, 0), Tuples.of(7, 0), Tuples.of(8, 0),
        Tuples.of(9, 0), Tuples.of(10, 2), Tuples.of(11, 1),
        Tuples.of(12, 0), Tuples.of(13, 3), Tuples.of(14, 2),
        Tuples.of(15, 3), Tuples.of(16, 6), Tuples.of(17, 3),
        Tuples.of(18, 1), Tuples.of(19, 1), Tuples.of(20, 0),
        Tuples.of(21, 0), Tuples.of(22, 1), Tuples.of(23, 0), Tuples.of(24, 0)
    )

    val books: List<Book> = listOf(
        Book("Advance Java", "Tom", "Tom-boy", 25000, 100),
        Book("Advance Python", "Grace", "Grace-girl", 22000, 150),
        Book("Advance Reactor", "Smith", "David-boy", 35000, 200),
        Book("Getting started Java", "Tom", "Tom-boy", 32000, 230),
        Book("Advance Kotlin", "Kevin", "Kevin-boy", 32000, 250),
        Book("Advance Javascript", "Mike", "Tom-boy", 32000, 320),
        Book("Getting started Kotlin", "Kevin", "Kevin-boy", 32000, 150),
        Book("Getting started Python", "Grace", "Grace-girl", 32000, 200),
        Book("Getting started Reactor", "Smith", null, 32000, 250),
        Book("Getting started Javascript", "Mike", "David-boy", 32000, 330)
    )

    val monthlyBookSales2021: List<Int> = listOf(
        2500000, 3200000, 2300000, 4500000,
        6500000, 5500000, 3100000, 2000000,
        2800000, 4100000, 6200000, 4200000
    )

    enum class CovidVaccine {
        Pfizer,
        AstraZeneca,
        Moderna,
        Janssen,
        Novavax;

        companion object {
            fun toList(): List<CovidVaccine> {
                return listOf(
                    Pfizer,
                    AstraZeneca,
                    Moderna,
                    Janssen,
                    Novavax
                )
            }
        }
    }
}