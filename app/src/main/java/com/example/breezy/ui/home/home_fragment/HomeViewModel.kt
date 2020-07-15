package com.example.breezy.ui.home.home_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breezy.model.remote.data.ApiManager
import com.example.breezy.model.remote.pojo.contents.ArticlesItem
import com.example.breezy.model.remote.pojo.contents.ContentsResponse
import com.example.breezy.model.remote.pojo.titles.SourcesItem
import com.example.breezy.model.remote.pojo.titles.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    val homeTitlesMutableLiveData = MutableLiveData<MutableList<SourcesItem>>()
    val homeContentMutableLiveData = MutableLiveData<MutableList<ArticlesItem>>()
    val homeErrorMutableLiveData = MutableLiveData<String>()

    //This method gets the titles of the news
    public fun getNewsSources(apiKey : String, category : String, country : String, language : String){

        ApiManager.apiServices.getNewsSources(apiKey, category, country, language).enqueue(object : Callback<SourcesResponse>{
            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                homeErrorMutableLiveData.postValue("No connection")
            }

            override fun onResponse(call: Call<SourcesResponse>, response: Response<SourcesResponse>) {
                if(response.isSuccessful){
                    response.body()?.sources?.let {
                        homeTitlesMutableLiveData.postValue(it)
                    }
                }

            }

        })

    }

    //This method gets the content of the news
    public fun getNewsContent(apiKey: String, source : String){

        ApiManager.apiServices.getNewsContent(apiKey, source).enqueue(object : Callback<ContentsResponse>{
            override fun onFailure(call: Call<ContentsResponse>, t: Throwable) {
                homeErrorMutableLiveData.postValue("No connection")
            }

            override fun onResponse(
                call: Call<ContentsResponse>, response: Response<ContentsResponse>) {
                if(response.isSuccessful){
                    response.body()?.articles?.let {
                        homeContentMutableLiveData.postValue(it)
                    }
                }
            }

        })

    }




}