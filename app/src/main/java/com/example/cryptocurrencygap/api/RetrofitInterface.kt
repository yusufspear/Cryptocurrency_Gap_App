package com.example.cryptocurrencygap.api

import android.content.Context
import com.example.cryptocurrencygap.model.CurrencyConverterAPI
import com.example.cryptocurrencygap.model.binance.Binance
import com.example.cryptocurrencygap.model.binance.BinanceCurrentPriceList
import com.example.cryptocurrencygap.model.wazirx.WazirX
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


abstract class RetrofitService() {

    companion object {
        private var instanceOfWazirX: RetrofitInterface? = null
        private var instanceOfBinance: RetrofitInterface? = null
        private var instanceOfCurrency: RetrofitInterface? = null
        fun getInstanceOfWazirX(context: Context): RetrofitInterface {
            if (instanceOfWazirX == null) {
                synchronized(this) {
                    val logger =
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    val client = OkHttpClient.Builder().addInterceptor(logger).build()
                    val retrofit = Retrofit.Builder().baseUrl("https://api.wazirx.com/")
                        .addConverterFactory(GsonConverterFactory.create()).client(client).build()

                    instanceOfWazirX = retrofit.create(RetrofitInterface::class.java)
                    return instanceOfWazirX!!
                }
            }
            return instanceOfWazirX!!
        }

        fun getInstanceOfBinance(context: Context): RetrofitInterface {
            if (instanceOfBinance == null) {
                synchronized(this) {
                    val logger =
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    val client = OkHttpClient.Builder().addInterceptor(logger).build()
                    val retrofit = Retrofit.Builder().baseUrl("https://api.binance.com/")
                        .addConverterFactory(GsonConverterFactory.create()).client(client).build()

                    instanceOfBinance = retrofit.create(RetrofitInterface::class.java)
                    return instanceOfBinance!!
                }
            }
            return instanceOfBinance!!
        }

        fun getInstanceOfCurrency(context: Context): RetrofitInterface {
            if (instanceOfCurrency == null) {
                synchronized(this) {
                    val logger =
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    val client = OkHttpClient.Builder().addInterceptor(logger).build()
                    val retrofit =
                        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://free.currconv.com/")
                            .client(client)
                            .build()
                    instanceOfCurrency = retrofit.create(RetrofitInterface::class.java)
                    return instanceOfCurrency!!
                }
            }
            return instanceOfCurrency!!

        }
    }
}

interface RetrofitInterface {


    //WazirX
    @GET("api/v2/market-status")
    suspend fun getWazirXData(): Response<WazirX>

    //Binance
    @GET("api/v3/ticker/24hr")
    suspend fun getBinanceData(): Response<List<Binance>>

    @GET("api/v3/ticker/price")
    suspend fun getBinanceCurrentPrice(): Response<ArrayList<BinanceCurrentPriceList>>

    //CurrencyConverterAPI
    //https://free.currconv.com/api/v7/convert?q=USD_INR&compact=ultra&apiKey=23e8cbdf5e07a94a4bfa
    @GET("api/v7/convert")
    suspend fun getCurrencyConverter(
        @Query("q") currency: String,
        @Query("compact") compact:String,
        @Query("apiKey") apiKey: String
    ): Response<CurrencyConverterAPI>

}