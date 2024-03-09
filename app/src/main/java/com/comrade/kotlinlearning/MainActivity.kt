package com.comrade.kotlinlearning

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.comrade.kotlinlearning.databinding.ActivityMainBinding
import com.comrade.kotlinlearning.model.Result
import com.devrev.devrevnetworkingmodule.MyJSONListener
import com.devrev.devrevnetworkingmodule.MyNetwork
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
   lateinit var binding: ActivityMainBinding
    val data = ArrayList<Result>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager(binding.tabViewpager)
        binding.tabTablayout.setupWithViewPager(binding.tabViewpager)
    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        // LoginFragment is the name of Fragment and the Login
        // is a title of tab
        adapter.addFragment(LatestFragment(), "Trending")
        adapter.addFragment(PopularFragment(), "Popular")

        // setting adapter to view pager.
        viewpager.setAdapter(adapter)
    }
}