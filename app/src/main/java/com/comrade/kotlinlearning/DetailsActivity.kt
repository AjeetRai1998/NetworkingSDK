package com.comrade.kotlinlearning

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.comrade.kotlinlearning.adapter.PopularAdapter
import com.comrade.kotlinlearning.databinding.ActivityDetailsBinding
import com.comrade.kotlinlearning.model.Result
import com.devrev.devrevnetworkingmodule.MyJSONListener
import com.devrev.devrevnetworkingmodule.MyNetwork
import org.json.JSONArray
import org.json.JSONObject

class DetailsActivity : AppCompatActivity() {
    lateinit var binding:ActivityDetailsBinding
    var genre:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DetailApiApi()
    }
    private  fun DetailApiApi() {
        val progressDialog= ProgressDialog.show(this,"","Loading")
        MyNetwork.Request(MyNetwork.GET)
            .url("https://api.themoviedb.org/3/movie/"+intent.getStringExtra("id")+"?language=en-US&api_key=909594533c98883408adef5d56143539")
            .makeRequest(object : MyJSONListener {
                override fun onResponse(res: JSONObject?) {
                    progressDialog.dismiss()
                    Log.d(ContentValues.TAG,res.toString())

                    val arr = res?.optJSONArray("genres")
                    var i=0
                    if (arr!=null){
                        while (i<arr.length()){
                            if (i==0){
                                genre=arr.getJSONObject(i).getString("name")
                            }else{
                                genre=genre+", "+arr.getJSONObject(i).getString("name")
                            }
                            i++
                        }


                    }


                    runOnUiThread {
                        Glide.with(this@DetailsActivity).load("https://media.themoviedb.org/t/p/w220_and_h330_face/" +res?.getString("poster_path")).into(binding.image)
                        binding.name.text=res?.getString("original_title")
                        binding.date.text=res?.getString("release_date")
                        binding.overview.text=res?.getString("overview")
                        binding.gener.text=genre
                        binding.budget.text=""+res?.getString("budget")
                        binding.collection.text=""+res?.getString("revenue")
                        binding.rating.text=""+res?.getString("vote_average")+"/"+res?.getString("vote_count")
                        binding.playtime.text=res?.getString("runtime")+"m"

                        val d=res?.getString("tagline")
                        binding.tag.text=d



                        Log.d(TAG,d.toString())

                    }




                }

                override fun onFailure(e: Exception?) {
                    progressDialog.dismiss()
                    e?.message?.let { Log.d(ContentValues.TAG, it) }
                    Toast.makeText(this@DetailsActivity,"failed", Toast.LENGTH_SHORT).show()

                }

            })
    }

    fun back(view: View) {
        finish()
    }

}