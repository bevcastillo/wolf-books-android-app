package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListPrice__1 {
    @SerializedName("amountInMicros")
    @Expose
    var amountInMicros: Long? = null

    @SerializedName("currencyCode")
    @Expose
    var currencyCode: String? = null
}