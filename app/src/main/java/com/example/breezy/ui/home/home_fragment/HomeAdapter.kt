package com.example.breezy.ui.home.home_fragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.breezy.R
import com.example.breezy.model.remote.pojo.contents.ArticlesItem
import kotlinx.android.synthetic.main.home_item.view.*
import java.util.*

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var data: MutableList<ArticlesItem> = ArrayList()
    lateinit var onItemClickListener: OnItemClickListener
    lateinit var onItemLongClickListener: OnItemLongClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: MutableList<ArticlesItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun clickData(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    fun longClickData(onItemLongClickListener: OnItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ArticlesItem) = with(itemView) {
            // TODO: Bind the data with View
            news_title_id.text = item.title
            news_time_id.text = item.publishedAt
            Glide.with(itemView).load(item.urlToImage).into(news_image_id)
            setOnClickListener {
                //    listener.invoke(it, item, adapterPosition)
                onItemClickListener.onItemClick(adapterPosition)
            }
            setOnLongClickListener {
                //    listener.invoke(it, item, adapterPosition)
                onItemLongClickListener.onItemLongClick(item)
                return@setOnLongClickListener true
            }

        }
    }

    interface OnItemClickListener{
        fun onItemClick(position : Int)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(item : ArticlesItem)
    }

    public fun getItem(position : Int) : ArticlesItem{
        return data[position]
    }
    public fun destroyData(){
        data.clear()
    }


}