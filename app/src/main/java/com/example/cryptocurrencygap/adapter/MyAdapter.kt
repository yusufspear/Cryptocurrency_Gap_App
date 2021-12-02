package com.example.cryptocurrencygap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencygap.MainActivity
import com.example.cryptocurrencygap.R
import com.example.cryptocurrencygap.databinding.RawBinding
import com.example.cryptocurrencygap.model.GapPriceList
import com.example.cryptocurrencygap.viewmodel.MainViewModel

class MyAdapter(private var cryptoList: ArrayList<GapPriceList>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    lateinit var binding: RawBinding
    lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RawBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.raw, parent, false)
        )
        context=parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

            tvMarket.text = cryptoList[position].getMarket()
            tvBinamce.text = cryptoList[position].getBinanceCoin().toString()
            tvWazirX.text = cryptoList[position].getWwazirCoin().toString()
            tvPriceGap.text = cryptoList[position].getGapPrice().toString()
            tvChange.text = cryptoList[position].getProfitPlatform()
        }
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }


    inner class ViewHolder(val binding: RawBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    fun filter(filterList: ArrayList<GapPriceList>) {

        filterList?.let {
            cryptoList.clear()
            cryptoList.addAll(it)
        }

    }
}