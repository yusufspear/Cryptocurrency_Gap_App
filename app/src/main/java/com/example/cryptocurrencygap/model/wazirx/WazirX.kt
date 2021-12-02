package com.example.cryptocurrencygap.model.wazirx


import com.google.gson.annotations.SerializedName


data class WazirX(

    @SerializedName("markets")
    val markets: List<Market>,
    @SerializedName("assets")
    val assets: List<Asset>,
)