package com.example.cryptocurrencygap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencygap.MainActivity
import com.example.cryptocurrencygap.R
import com.example.cryptocurrencygap.databinding.CurrencyListBinding
import com.example.cryptocurrencygap.databinding.RawBinding
import com.example.cryptocurrencygap.model.GapPriceList
import com.example.cryptocurrencygap.viewmodel.MainViewModel

class CurrencyListAdapter(private var cryptoList: ArrayList<GapPriceList>) :
    RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {
    lateinit var binding: CurrencyListBinding
    lateinit var context: Context



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CurrencyListBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_currency_list, parent, false)
        )
        context=parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

            textView.text = cryptoList[position].getMarket()
            checkBox.setOnCheckedChangeListener { view, state ->

            }

        }
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }


    inner class ViewHolder(val binding: CurrencyListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}