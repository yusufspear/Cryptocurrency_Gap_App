package com.example.cryptocurrencygap.model.wazirx


import com.google.gson.annotations.SerializedName

data class Market(

    @SerializedName("baseMarket")
    val baseMarket: String,
    @SerializedName("quoteMarket")
    val quoteMarket: String,

    @SerializedName("fee")
    val fee: Fee,

    @SerializedName("low")
    val low: String,
    @SerializedName("high")
    val high: String,
    @SerializedName("last")
    val last: String,

    @SerializedName("buy")
    val buy: String,
    @SerializedName("sell")
    val sell: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("status")
    val status: String,
)