package com.example.breezy.model.local.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.breezy.model.local.pojo.Bookmark
import kotlinx.coroutines.Dispatchers

@Database(entities = arrayOf(Bookmark::class), version = 1, exportSchema = false)
abstract class BookmarkManager : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    companion object {
        val DATABASE_NAME = "bookmarks"
        var bookmarkDatabase: BookmarkManager? = null
        suspend fun getInstance(context: Context): BookmarkManager {
                if (bookmarkDatabase == null) {
                    bookmarkDatabase = Room.databaseBuilder(
                        context,
                        BookmarkManager::class.java,
                        DATABASE_NAME
                    ).build()
                }
                return bookmarkDatabase as BookmarkManager
        }

    }
}