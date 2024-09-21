package com.bevstudio.wolfbooksapp.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {
    @SerializedName("kind")
    @Expose
    var kind: String? = null

    @JvmField
    @SerializedName("id")
    @Expose
    var id: String? = null

    @JvmField
    @SerializedName("volumeInfo")
    @Expose
    var volumeInfo: VolumeInfo? = null

    @SerializedName("saleInfo")
    @Expose
    var saleInfo: SaleInfo? = null

    @SerializedName("accessInfo")
    @Expose
    var accessInfo: AccessInfo? = null

    @SerializedName("searchInfo")
    @Expose
    var searchInfo: SearchInfo? = null
}