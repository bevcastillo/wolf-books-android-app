package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AccessInfo {
    @SerializedName("webReaderLink")
    @Expose
    val webReaderLink: String? = null
}