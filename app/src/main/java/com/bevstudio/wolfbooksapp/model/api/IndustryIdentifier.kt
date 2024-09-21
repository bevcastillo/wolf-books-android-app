package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IndustryIdentifier {
    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("identifier")
    @Expose
    var identifier: String? = null
}