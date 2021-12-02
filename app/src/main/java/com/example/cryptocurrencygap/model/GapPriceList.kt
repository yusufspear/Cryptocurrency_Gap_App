package com.example.cryptocurrencygap.model

data class GapPriceList(
    private val Market: String,
    private val wazirCoin: Float,
    private val binanceCoin: Float,
    private val gapPrice: Float,
    private val profitPlatform: String
){

    fun getMarket()=this.Market
    fun getWwazirCoin()=this.wazirCoin
    fun getBinanceCoin()=this.binanceCoin
    fun getGapPrice()=this.gapPrice
    fun getProfitPlatform()=this.profitPlatform
}


