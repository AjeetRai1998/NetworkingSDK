package com.comrade.kotlinlearning.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.comrade.kotlinlearning.R
import com.comrade.kotlinlearning.model.Result
import com.comrade.kotlinlearning.onClickEvent

class PopularAdapter(private val dataItem:List<Result>,val click:onClickEvent):
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_item, parent, false)

        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
        holder.name.text=dataItem.get(position).original_title
        holder.date.text=dataItem.get(position).release_date
        Glide.with(holder.itemView.context).load(
            "https://media.themoviedb.org/t/p/w220_and_h330_face/" + dataItem.get(position).poster_path
        ).into(holder.imageView)

        holder.card.setOnClickListener{
            click.click(""+dataItem.get(position).id)
        }


    }

    override fun getItemCount(): Int {
        return dataItem.size
    }
      class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.Image)
        val name: TextView = itemView.findViewById(R.id.name)
        val date: TextView = itemView.findViewById(R.id.date)
        val card: CardView = itemView.findViewById(R.id.card)
    }
}

