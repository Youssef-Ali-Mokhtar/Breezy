package com.example.breezy.ui.home.details

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
import kotlinx.android.synthetic.main.activity_home_details.*
import kotlinx.android.synthetic.main.home_item.view.*

class HomeDetailsActivity : AppCompatActivity() {
    private lateinit var homeDetailsViewModel: HomeDetailsViewModel
    companion object{
        private lateinit var url : String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_details)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        val intent = getIntent()
        var itemPosition = intent.getIntExtra("item_position", 0)

        url_id.setOnClickListener {
            openWebPage(url)
        }

        homeDetailsViewModel = ViewModelProvider(this).get(HomeDetailsViewModel::class.java)
        homeDetailsViewModel.getNewsDetails(itemPosition)
        setObservers()

    }
    private fun setObservers(){
        homeDetailsViewModel.homeDetailsMutableLiveData.observe(this, Observer {
            title_details_id.text = it.title
            Glide.with(this).load(it.urlToImage).into(image_details_id)
            published_at_time_id.text = it.publishedAt
            description_details_id.text = it.description
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