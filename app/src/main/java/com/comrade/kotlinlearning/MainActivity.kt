package com.comrade.kotlinlearning

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.devrev.devrevnetworkingmodule.MyJSONListener
import com.devrev.devrevnetworkingmodule.MyNetwork
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyNetwork.Request(MyNetwork.GET)
            .url("https://dummyjson.com/products/1")
            .makeRequest(object : MyJSONListener{
                override fun onResponse(res: JSONObject?) {
                    Log.d(TAG,res.toString())
                }

                override fun onFailure(e: Exception?) {
                    e?.message?.let { Log.d(TAG, it) }
                }

            })

    }
}