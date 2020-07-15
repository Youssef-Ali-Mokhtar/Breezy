package com.example.breezy.ui.home.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breezy.model.remote.pojo.contents.ArticlesItem
import com.example.breezy.ui.home.home_fragment.HomeFragment

class HomeDetailsViewModel : ViewModel(){
    val homeDetailsMutableLiveData = MutableLiveData<ArticlesItem>()

    var homeFragment = HomeFragment()

    public fun getNewsDetails(position : Int){
        homeDetailsMutableLiveData.postValue(getData(position))
    }

    private fun getData(position : Int) : ArticlesItem{
        return homeFragment.getItem(position)
    }


}