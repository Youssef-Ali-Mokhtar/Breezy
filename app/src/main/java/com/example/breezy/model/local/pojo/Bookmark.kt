package com.example.breezy.model.local.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bookmark(
    val title:String,
    val image:String,
    val author:String,
    val publishedAt:String,
    val description:String,
    val url:String
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}