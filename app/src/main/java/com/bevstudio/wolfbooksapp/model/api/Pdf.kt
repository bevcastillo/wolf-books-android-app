package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pdf {
    @SerializedName("isAvailable")
    @Expose
    var isAvailable: Boolean? = null

    @SerializedName("acsTokenLink")
    @Expose
    var acsTokenLink: String? = null
}