package com.example.breezy.ui.bookmarks.bookmarks_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breezy.model.local.pojo.Bookmark
import com.example.breezy.ui.bookmarks.bookmarks_fragment.BookmarksFragment

class BookmarksDetailsViewModel : ViewModel(){
    val bookmarksDetailsMutableLiveData = MutableLiveData<Bookmark>()

    var bookmarksFragment = BookmarksFragment()

    public fun getBookmarksDetails(position : Int){
        bookmarksDetailsMutableLiveData.postValue(getData(position))
    }

    private fun getData(position : Int) : Bookmark {
        return bookmarksFragment.getItem(position)
    }
}