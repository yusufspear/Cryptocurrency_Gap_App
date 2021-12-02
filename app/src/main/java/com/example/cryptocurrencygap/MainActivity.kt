package com.example.cryptocurrencygap

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.d
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencygap.adapter.MyAdapter
import com.example.cryptocurrencygap.api.RetrofitService
import com.example.cryptocurrencygap.databinding.ActivityMainBinding
import com.example.cryptocurrencygap.model.GapPriceList
import com.example.cryptocurrencygap.repository.Repository
import com.example.cryptocurrencygap.viewmodel.MainViewModel
import com.example.cryptocurrencygap.viewmodel.MyViewModelFactory
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity "
    lateinit var mViewModel: MainViewModel


    private var list = ArrayList<GapPriceList>()
    private lateinit var adapter: MyAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            searchView.setOnSearchClickListener {
                Toast.makeText(
                    applicationContext,
                    "SearchView is Clicked!",
                    Toast.LENGTH_LONG
                ).show()
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(nextText: String?): Boolean {
                    Toast.makeText(applicationContext, "$nextText", Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onQueryTextChange(nextText: String?): Boolean {
                    d(TAG, "query is $nextText")
//                    Toast.makeText(applicationContext, "$nextText", Toast.LENGTH_SHORT).show()
//
//                    val fragment=CurrencyFragment()
//                    val manager=supportFragmentManager.beginTransaction().add(fragment,fragment)

                    val filterList = ArrayList<GapPriceList>()
                    if (nextText?.isNotEmpty() == true) {
                        for (item in 0 until list.size) {
                            d(TAG, " Before filterList is $filterList")

                            if (list[item].getMarket().lowercase(Locale.getDefault())
                                    .contains(nextText.lowercase(Locale.getDefault()))
                            ) {
                                filterList.add(list[item])
                                d(TAG, "After filterList is $filterList")

                            }
                        }
                    }

                    return false
                }
            })
            searchView.setOnCloseListener {
                Toast.makeText(applicationContext, "SearchView is Close", Toast.LENGTH_LONG)
                    .show()
                false
            }

            recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyAdapter(list)
            recyclerview.adapter = adapter
            val repository = Repository(
                RetrofitService.getInstanceOfWazirX(applicationContext),
                RetrofitService.getInstanceOfBinance(applicationContext),
                RetrofitService.getInstanceOfCurrency(applicationContext)
            )
            mViewModel =
                ViewModelProvider(this@MainActivity, MyViewModelFactory(repository))
                    .get(MainViewModel::class.java)


            mViewModel.getData().observe(this@MainActivity, androidx.lifecycle.Observer {
                list.clear()
                list.addAll(it)

                d(TAG, "List value is $list")
                d("${TAG}showData", it.toString())


                recyclerview.adapter?.notifyDataSetChanged()

            })


        }
    }


}


