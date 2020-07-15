package com.example.breezy.ui.bookmarks.bookmarks_details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.breezy.R
import kotlinx.android.synthetic.main.activity_bookmarks_details.*


class BookmarksDetailsActivity : AppCompatActivity() {
    private lateinit var bookmarksDetailsViewModel: BookmarksDetailsViewModel
    companion object{
        private lateinit var url : String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks_details)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        val intent = getIntent()
        var itemPosition = intent.getIntExtra("item_position", 0)

        bookmarks_url_id.setOnClickListener {
            openWebPage(url)
        }

        bookmarksDetailsViewModel = ViewModelProvider(this).get(BookmarksDetailsViewModel::class.java)
        bookmarksDetailsViewModel.getBookmarksDetails(itemPosition)
        setObservers()
    }

    private fun setObservers() {
        bookmarksDetailsViewModel.bookmarksDetailsMutableLiveData.observe(this, Observer {
            bookmarks_title_details_id.text = it.title
            Glide.with(this).load(it.image).into(bookmarks_image_details_id)
            bookmarks_published_at_time_id.text = it.publishedAt
            bookmarks_description_details_id.text = it.description
            url = it.url
        })
    }

    fun openWebPage(url: String?) {
        try {
            val webpage: Uri = Uri.parse(url)
            val myIntent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No application can handle this request. Please install a web browser or check your URL.",
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
    }

}