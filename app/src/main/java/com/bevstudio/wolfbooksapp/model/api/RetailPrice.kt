package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetailPrice {
    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("currencyCode")
    @Expose
    var currencyCode: String? = null
}