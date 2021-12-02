package com.example.cryptocurrencygap.model.binance


import com.google.gson.annotations.SerializedName

data class Binance(

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("priceChange")
    val priceChange: String,
    @SerializedName("priceChangePercent")
    val priceChangePercent: String,

    @SerializedName("weightedAvgPrice")
    val weightedAvgPrice: String,
    @SerializedName("prevClosePrice")
    val prevClosePrice: String,
    @SerializedName("openPrice")
    val openPrice: String,

    @SerializedName("lowPrice")
    val lowPrice: String,
    @SerializedName("highPrice")
    val highPrice: String,
    @SerializedName("lastPrice")
    val lastPrice: String,


    @SerializedName("askPrice")
    val askPrice: String,
    @SerializedName("askQty")
    val askQty: String,
    @SerializedName("bidPrice")
    val bidPrice: String,
    @SerializedName("bidQty")
    val bidQty: String,

    )