package com.example.cryptocurrencygap.model


import com.google.gson.annotations.SerializedName

data class CurrencyConverterAPI(
    @SerializedName("USD_INR")
    val USD_INR: Double
)
