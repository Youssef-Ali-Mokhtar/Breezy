package com.example.breezy.model.remote.pojo.titles

import com.example.breezy.model.remote.pojo.titles.SourcesItem
import com.google.gson.annotations.SerializedName

data class SourcesResponse(

	@field:SerializedName("sources")
	val sources: MutableList<SourcesItem>,

	@field:SerializedName("status")
	val status: String
)