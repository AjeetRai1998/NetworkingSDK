package com.comrade.kotlinlearning

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.comrade.kotlinlearning.adapter.PopularAdapter
import com.comrade.kotlinlearning.databinding.FragmentLatestBinding
import com.comrade.kotlinlearning.model.Result
import com.devrev.devrevnetworkingmodule.MyJSONListener
import com.devrev.devrevnetworkingmodule.MyNetwork
import org.json.JSONArray
import org.json.JSONObject

class LatestFragment : Fragment(), onClickEvent {
    lateinit var binding: FragmentLatestBinding
    val data = ArrayList<Result>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLatestBinding.inflate(inflater, container, false)
        binding.latest.hasFixedSize()
        binding.latest.layoutManager = GridLayoutManager(context, 2)
        if (context?.let { CheckNetwork.checkConnectivity(it) } == true)
            TrendingApi()
        else
            Toast.makeText(context, "No internet Connection !", Toast.LENGTH_SHORT).show()


        return binding.root
    }

    private fun TrendingApi() {
        val progressDialog = ProgressDialog.show(context, "", "Loading")
        MyNetwork.Request(MyNetwork.GET)
            .url("https://api.themoviedb.org/3/trending/movie/day?language=en-US&api_key=909594533c98883408adef5d56143539")
            .makeRequest(object : MyJSONListener {
                override fun onResponse(res: JSONObject?) {
                    progressDialog.dismiss()
                    Log.d(ContentValues.TAG, res.toString())
                    val arr = res?.optJSONArray("results")
                    Log.d(ContentValues.TAG, arr.toString())
                    var i = 0
                    data.clear()
                    if (arr != null) {
                        while (i < arr.length()) {
                            val a = arrayListOf<JSONArray>(
                                arr.getJSONObject(i).getJSONArray("genre_ids")
                            )
                            val result = Result(
                                arr.getJSONObject(i).getBoolean("adult"),
                                arr.getJSONObject(i).getString("backdrop_path"),
                                a,
                                arr.getJSONObject(i).getInt("id"),
                                arr.getJSONObject(i).getString("original_language"),
                                arr.getJSONObject(i).getString("original_title"),
                                arr.getJSONObject(i).getString("overview"),
                                arr.getJSONObject(i).getDouble("popularity"),
                                arr.getJSONObject(i).getString("poster_path"),
                                arr.getJSONObject(i).getString("release_date"),
                                arr.getJSONObject(i).getString("title"),
                                arr.getJSONObject(i).getBoolean("video"),
                                arr.getJSONObject(i).getDouble("vote_average"),
                                arr.getJSONObject(i).getInt("vote_count")
                            )
                            data.add(result)
                            i++
                        }


                        val popularAdapter = PopularAdapter(data, this@LatestFragment)
                        activity?.runOnUiThread {
                            binding.latest.adapter = popularAdapter

                        }
                    }

                }

                override fun onFailure(e: Exception?) {
                    progressDialog.dismiss()
                    e?.message?.let { Log.d(ContentValues.TAG, it) }
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()

                }

            })

    }

    override fun click(id: String) {
        val intent= Intent(context,DetailsActivity::class.java)
        intent.putExtra("id",id)
        startActivity(intent)

    }

}