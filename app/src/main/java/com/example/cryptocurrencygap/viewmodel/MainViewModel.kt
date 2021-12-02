package com.example.cryptocurrencygap.viewmodel

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencygap.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val TAG = "MainViewModel "
    private var searchBarActive: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val isSearchBarActive : MutableLiveData<Boolean>
        get() =searchBarActive

    init {
        val time = 100

        val job = viewModelScope
        val job1 = job.launch(Dispatchers.IO) {
            repeat(time){

                repository.CurrencyConverter().getCurrency()
                d("$TAG  ", "Done..")

                delay(30000)
            }
        }
        val job2 = job.launch(Dispatchers.IO) {
            repeat(time) {
                repository.Wazir().getWazirXData()
                d("$TAG  ", "I'm Running...in WazirX${it}")
                delay(1000)
            }

        }

        val job3 = job.launch(Dispatchers.IO) {
            repeat(time) {
                repository.Binance().getBinanceData()
                d("$TAG  ", "I'm Running... in Binance${it}")
                delay(2500)
            }

        }

    }

    fun setSearchBarStatus(data : Boolean){
        d(TAG,"setSearchBarStatus function is working..")

        searchBarActive.postValue(data)
    }

    fun getData()=repository.getGapPriceList


}