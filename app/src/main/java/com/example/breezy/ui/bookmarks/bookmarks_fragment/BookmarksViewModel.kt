package com.example.breezy.ui.bookmarks.bookmarks_fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breezy.model.local.data.BookmarkManager
import com.example.breezy.model.local.pojo.Bookmark
import com.example.breezy.model.remote.pojo.contents.ArticlesItem
import com.example.breezy.ui.home.home_fragment.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BookmarksViewModel : ViewModel() {

    val addBookmarkMutableLiveData = MutableLiveData<String>()
    val getAllBookmarksMutableLiveData = MutableLiveData<ArrayList<Bookmark>>()
    val deleteBookmarkMutableLiveData = MutableLiveData<String>()

    public fun addBookmark(bookmark: ArticlesItem, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val bookmark = Bookmark(
                bookmark.title,
                bookmark.urlToImage,
                bookmark.author,
                bookmark.publishedAt,
                bookmark.description,
                bookmark.url
            )
            addBookmarkToDatabase(context, bookmark)
            addBookmarkMutableLiveData.postValue("Bookmark added")
        }
    }

    public fun getAllBookmarks(context : Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val allBookmarks = async {getAllBookmarksFromDatabase(context)}
            getAllBookmarksMutableLiveData.postValue(allBookmarks.await())
        }
    }

    public fun deleteBookmark(id : Int, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            deleteBookmarkFromDatabase(id, context)
            deleteBookmarkMutableLiveData.postValue("Bookmark deleted")
        }
    }

//    BookmarkManager.getInstance(context)?.bookmarkDao()?.addBookmark(bookmark)
    suspend private fun addBookmarkToDatabase(context : Context, bookmark : Bookmark) {
        BookmarkManager.getInstance(context)?.bookmarkDao()?.addBookmark(bookmark)
    }

    suspend private fun getAllBookmarksFromDatabase(context : Context): ArrayList<Bookmark> {
        return BookmarkManager.getInstance(context)?.bookmarkDao()?.getAllBookmarks() as ArrayList<Bookmark>
    }

    suspend private fun deleteBookmarkFromDatabase(id : Int, context: Context){
        BookmarkManager.getInstance(context)?.bookmarkDao()?.deleteBookmarkById(id)
    }


}