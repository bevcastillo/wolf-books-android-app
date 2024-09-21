package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageLinks {
    @JvmField
    @SerializedName("smallThumbnail")
    @Expose
    val smallThumbnail: String? = null

    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String? = null
}