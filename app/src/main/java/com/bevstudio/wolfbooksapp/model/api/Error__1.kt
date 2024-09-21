package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Error__1 {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("domain")
    @Expose
    var domain: String? = null

    @SerializedName("reason")
    @Expose
    var reason: String? = null

    @SerializedName("location")
    @Expose
    var location: String? = null

    @SerializedName("locationType")
    @Expose
    var locationType: String? = null
}