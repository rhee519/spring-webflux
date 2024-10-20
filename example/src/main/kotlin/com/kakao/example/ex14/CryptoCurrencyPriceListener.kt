package com.kakao.example.ex14

interface CryptoCurrencyPriceListener {
    fun onPrice(priceList: List<Int>)
    fun onComplete()
}