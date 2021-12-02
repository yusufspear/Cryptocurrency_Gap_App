package com.example.cryptocurrencygap.repository

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import com.example.cryptocurrencygap.api.RetrofitInterface
import com.example.cryptocurrencygap.model.GapPriceList
import com.example.cryptocurrencygap.model.binance.BinanceCurrentPriceList
import com.example.cryptocurrencygap.model.wazirx.Market
import com.example.cryptocurrencygap.model.wazirx.WazirX
import kotlinx.coroutines.delay
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class Repository(
    private val instanceOfWaxirX: RetrofitInterface,
    private val instanceOfBinance: RetrofitInterface,
    private val instanceOfCurrency: RetrofitInterface,

    ) {

    private val TAG = "FromRepository "

    private val USD_INR = MutableLiveData<Double>()

    //user Data
    //UserCoinList Details
    private val userCoinList: ArrayList<String> = arrayListOf("btc", "wrx", "bat", "xrp", "matic")
    var isWazirXAPIFirstTime = true
    var isBinanceAPIFirstTime = true

    private val data = MutableLiveData<ArrayList<GapPriceList>>()

    val getGapPriceList: MutableLiveData<ArrayList<GapPriceList>>
        get() = data


    //WazirX Data
    private var WazirXLiveData = MutableLiveData<WazirX>()
    val getWazirXData: MutableLiveData<WazirX>
        get() = WazirXLiveData



    private val INRList = MutableLiveData<ArrayList<String>>()
    private val INRIndexListofWazirX: MutableLiveData<MutableMap<String, Float>> =
        MutableLiveData<MutableMap<String, Float>>()


    //Binance
    //Binance Data
    private val binanceCoinData = MutableLiveData<ArrayList<BinanceCurrentPriceList>>()
    val getBinanceCoinList: MutableLiveData<ArrayList<BinanceCurrentPriceList>>
        get() = binanceCoinData

    //USD Symbol List
    private val usdList = MutableLiveData<ArrayList<BinanceCurrentPriceList>>()
    val getBinanceUSDList: MutableLiveData<ArrayList<BinanceCurrentPriceList>>
        get() = usdList

    //INR Value List
    private val binanceINRValueList = MutableLiveData<ArrayList<Float>>()
    val getBinanceInrList: MutableLiveData<ArrayList<Float>>
        get() = binanceINRValueList

    private val binanceMapList: MutableLiveData<MutableMap<String, Int>> =
        MutableLiveData<MutableMap<String, Int>>()
    private val binanceINRMapList: MutableLiveData<MutableMap<String, Float>> =
        MutableLiveData<MutableMap<String, Float>>()


    inner class CurrencyConverter() {
        suspend fun getCurrency() {

            val result =
                instanceOfCurrency.getCurrencyConverter("USD_INR", "ultra", "23e8cbdf5e07a94a4bfa")
            if (result.body() != null) {
                USD_INR.postValue(result.body()!!.USD_INR)
                d(TAG, "USD to INR" + result.body()!!.USD_INR)
            }
        }
    }


    inner class Wazir() {
        //Call From ViewModel to get WazirX Data from API
        suspend fun getWazirXData() {
            val result = instanceOfWaxirX.getWazirXData()

            if (result?.body() != null) {

//                if (isWazirXAPIFirstTime) {
                    getINRListFromWazir(result.body()!!.markets)
//                    isWazirXAPIFirstTime = false
                    d(TAG, isWazirXAPIFirstTime.toString())

//                }
                WazirXLiveData.postValue(result.body())

            }
        }


        //creating a list of INR type Coins FROM WazirX
        suspend fun getINRListFromWazir(market: List<Market>) {
            val temp1 = ArrayList<String>()
            val temp2 = mutableMapOf<String, Float>()
            market.forEach {

                if (it.quoteMarket == "inr" && it.type == "SPOT") {
                    temp1.add(it.baseMarket + "/" + it.quoteMarket)

                    temp2[it.baseMarket + "/" + it.quoteMarket] = it.last.toFloat()
                }

            }
            //List of INR Coins
            INRList.postValue(temp1)
            //Index of INR Coins in Market List
            INRIndexListofWazirX.postValue(temp2)

            delay(50)
            d(TAG, "List of INR WazirX"+INRList.value.toString() + "\n " + INRIndexListofWazirX.value.toString())


        }


    }

    inner class Binance() {

        //Call From ViewModel to get Binance Data from API
        suspend fun getBinanceData() {
            val result = instanceOfBinance.getBinanceCurrentPrice()
            if (result.body() != null) {
                if (isBinanceAPIFirstTime) {
                    isBinanceAPIFirstTime = false
                    convertToLowerCase(result.body())
                }

                binanceCoinData.postValue(result.body())
//            d(TAG, "Binance Data :" + binanceCoinData.value.toString())
                if (binanceMapList.value != null) {
                    d(TAG, "BinanceCoinData :" + binanceMapList.value.toString())

                    toInr(result.body(), binanceMapList.value!!)
                }

            }

        }

        private suspend fun toInr(
            value: ArrayList<BinanceCurrentPriceList>?,
            value1: MutableMap<String, Int>
        ) {
            val temp = ArrayList<Float>()
            val temp2 = mutableMapOf<String, Float>()
            d(TAG, "toInr value ${userCoinList.size}")
            d(TAG, "toInr value1 ${value1.values.toString()}")



            for (i in 0 until userCoinList.size) {

                val key = userCoinList[i] + "/" + "usdt"
                d(TAG, "toInr value $key  $i")

                val no: Int = value1[key]!!
                d(TAG, "toInr value $no")



                temp.add((value?.get(no)?.price!!.toFloat() * USD_INR.value!!.toFloat()))
                temp2[userCoinList[i] + "/" + "inr"] = temp[i]


            }


            binanceINRValueList.postValue(temp)
            binanceINRMapList.postValue(temp2)
            d(TAG, "toInr value final INR value" + binanceINRValueList.value.toString())
            d(TAG, "List of INR Binance" + binanceINRMapList.value.toString())


            while (true) {
                d(TAG, "Map Value Binance:${binanceINRMapList.value.toString()}")
                if (binanceINRMapList.value != null && INRIndexListofWazirX.value != null) {
                    d(TAG, "Map Value WazirX:${INRIndexListofWazirX.value.toString()}")
                    savedData(binanceINRMapList.value!!, INRIndexListofWazirX.value!!, userCoinList)
                    break
                }
                d(TAG, "Delaying.. 50ms")
                delay(50)

            }


        }

        private fun convertToLowerCase(value: ArrayList<BinanceCurrentPriceList>?) {

            val temp2 = mutableMapOf<String, Int>()
            for (i in 0 until value!!.size - 1) {
                val pattern = Regex("([A-Z]+(USDT))")
                if (pattern.matches(value[i].symbol)) {
                    val temp = value[i].symbol.lowercase(Locale.getDefault())
                    val split = temp.split("usdt")
                    d(TAG, "splitList $temp")
                    temp2[split[0] + "/" + "usdt"] = i
                }

            }
            binanceMapList.postValue(temp2)


        }


    }

    private suspend fun savedData(
        binance: MutableMap<String, Float>,
        wazirX: MutableMap<String, Float>,
        userCoinList: ArrayList<String>
    ) {
        val temp = ArrayList<GapPriceList>()
        for (i in 0 until userCoinList.size) {
            val coin: String = userCoinList[i] + "/" + "inr"
            d(TAG, " saveData Coin value $coin")

            if (binance.contains(coin) && wazirX.contains(coin)) {
                val market = coin.uppercase(Locale.getDefault()).split("/")
                d(TAG, " saveData Market value $market")
                val wazirPrice: Float = wazirX[coin]?.toFloat()!!
                d(TAG, " saveData wazirPrice value $wazirPrice")

                val binancePrice: Float = binance[coin]?.toFloat()!!
                d(TAG, " saveData binancePrice value $binancePrice")

                val priceDifferent = abs(wazirPrice - binancePrice)
                d(TAG, " saveData priceDifferent value $priceDifferent")

                val profitPlatform: String = if (wazirPrice > binancePrice) "WazirX" else "Binance"
                d(TAG, " saveData profitPlatform value $profitPlatform")

                temp.add(
                    GapPriceList(
                        market[0],
                        wazirPrice,
                        binancePrice,
                        priceDifferent,
                        profitPlatform
                    )
                )
                d(TAG, " saveData tempArrayList value $temp")
                d(TAG, " saveData tempArrayList Size ${temp.size}")


            }
        }
        data.postValue(temp)

        d(TAG, " saveData Final LiveData List Size ${temp[0]}")
        d(TAG, " saveData Final LiveData List Size ${data.value.toString()}")
        delay(10)
        d(TAG, " saveData getGapPriceList ${getGapPriceList.value.toString()}")


    }


}