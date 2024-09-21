package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Books {
    @SerializedName("kind")
    @Expose
    private val kind: String? = null

    @SerializedName("totalItems")
    @Expose
    val totalItems: Int? = null

    @SerializedName("items")
    @Expose
    var items: List<Item>? = null
}
