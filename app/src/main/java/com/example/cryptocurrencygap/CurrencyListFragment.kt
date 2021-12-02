package com.example.cryptocurrencygap

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptocurrencygap.databinding.FragmentCurrencyListBinding

class CurrencyListFragment : Fragment() {

    private lateinit var binding:FragmentCurrencyListBinding
     var list=ArrayList<String>()
    val checkBoxList= mutableMapOf<String,Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyListBinding.bind(inflater.inflate(R.layout.fragment_currency_list, container, false))
            return binding.root
    }

    companion object {

        fun newInstance(context: Context) =
            CurrencyListFragment().apply {
                arguments = Bundle().apply {



                }
            }
    }
}