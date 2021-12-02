package com.example.cryptocurrencygap.model.wazirx


import com.google.gson.annotations.SerializedName

data class Asset(

    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,

    @SerializedName("deposit")
    val deposit: String,
    @SerializedName("withdrawal")
    val withdrawal: String,

    @SerializedName("withdrawFee")
    val withdrawFee: Double,

    @SerializedName("minDepositAmount")
    val minDepositAmount: Double,
    @SerializedName("minWithdrawAmount")
    val minWithdrawAmount: String,
    @SerializedName("maxWithdrawAmount")
    val maxWithdrawAmount: Double,


    )