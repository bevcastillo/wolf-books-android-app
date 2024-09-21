package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Dimensions {
    @SerializedName("height")
    @Expose
    val height: String? = null

    @SerializedName("width")
    @Expose
    val width: String? = null

    @SerializedName("thickness")
    @Expose
    val thickness: String? = null
}