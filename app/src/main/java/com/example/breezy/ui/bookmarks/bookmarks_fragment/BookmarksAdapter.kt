package com.example.breezy.ui.bookmarks.bookmarks_fragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.breezy.R
import com.example.breezy.model.local.pojo.Bookmark
import com.example.breezy.model.remote.pojo.contents.ArticlesItem
import com.example.breezy.ui.home.home_fragment.HomeAdapter
import kotlinx.android.synthetic.main.home_item.view.*
import java.util.*

class BookmarksAdapter() : RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {

    private var data: ArrayList<Bookmark> = ArrayList()
    lateinit var onItemClickListener: OnItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        return BookmarksViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: ArrayList<Bookmark>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun clickData(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }
    inner class BookmarksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Bookmark) = with(itemView) {
            // TODO: Bind the data with View
            news_title_id.text = item.title
            news_time_id.text = item.publishedAt
            Glide.with(itemView).load(item.image).into(news_image_id)
            setOnClickListener {
                onItemClickListener.onItemClick(adapterPosition)
            }
        }
    }

    public fun getItemById(id : Int):Bookmark{
        return data[id]
    }

    public fun getAllData(): ArrayList<Bookmark>{
        return data
    }

    interface OnItemClickListener{
        fun onItemClick(position : Int)
    }

}