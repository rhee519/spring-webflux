package com.kakao.example.ex14

class CryptoCurrencyPriceEmitter {
    private var listener: CryptoCurrencyPriceListener? = null

    fun setListener(listener: CryptoCurrencyPriceListener?) {
        this.listener = listener
    }

    fun flowInto() {
        listener?.onPrice(SampleData.btcPrices)
    }

    fun complete() {
        listener?.onComplete()
    }
}