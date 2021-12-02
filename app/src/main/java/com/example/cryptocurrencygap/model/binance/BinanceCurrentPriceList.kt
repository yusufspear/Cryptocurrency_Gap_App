package com.example.cryptocurrencygap.model.binance


import com.google.gson.annotations.SerializedName

data class BinanceCurrentPriceList(
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("price")
    val price: String
)