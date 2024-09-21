package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SaleInfo {
    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("saleability")
    @Expose
    var saleability: String? = null

    @SerializedName("isEbook")
    @Expose
    var isEbook: Boolean? = null

    @SerializedName("listPrice")
    @Expose
    var listPrice: ListPrice? = null

    @SerializedName("retailPrice")
    @Expose
    var retailPrice: RetailPrice? = null

    @SerializedName("buyLink")
    @Expose
    var buyLink: String? = null

    @SerializedName("offers")
    @Expose
    var offers: List<Offer>? = null
}