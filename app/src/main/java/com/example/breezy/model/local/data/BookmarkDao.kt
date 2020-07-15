package com.example.breezy.model.local.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.breezy.model.local.pojo.Bookmark


@Dao
interface BookmarkDao {
    @Insert
    fun addBookmark(bookmark : Bookmark)

    @Query("select * from Bookmark")
    fun getAllBookmarks() : List<Bookmark>

    @Query("delete from Bookmark where id=:id")
    fun deleteBookmarkById(id : Int)
}