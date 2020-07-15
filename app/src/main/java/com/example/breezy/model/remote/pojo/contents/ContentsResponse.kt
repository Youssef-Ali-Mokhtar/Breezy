package com.example.breezy.model.remote.pojo.contents

import com.google.gson.annotations.SerializedName

data class ContentsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int,

	@field:SerializedName("articles")
	val articles: MutableList<ArticlesItem>,

	@field:SerializedName("status")
	val status: String
)