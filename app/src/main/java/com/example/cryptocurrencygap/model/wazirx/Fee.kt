package com.example.cryptocurrencygap.model.wazirx


import com.google.gson.annotations.SerializedName

data class Fee(
    @SerializedName("ask")
    val ask: Any,
    @SerializedName("bid")
    val bid: Any
)