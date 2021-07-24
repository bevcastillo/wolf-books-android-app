package com.example.bookfinderapp.modelV2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("finskyOfferType")
    @Expose
    private Integer finskyOfferType;
    @SerializedName("listPrice")
    @Expose
    private ListPrice__1 listPrice;
    @SerializedName("retailPrice")
    @Expose
    private RetailPrice__1 retailPrice;

    public Integer getFinskyOfferType() {
        return finskyOfferType;
    }

    public void setFinskyOfferType(Integer finskyOfferType) {
        this.finskyOfferType = finskyOfferType;
    }

    public ListPrice__1 getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPrice__1 listPrice) {
        this.listPrice = listPrice;
    }

    public RetailPrice__1 getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(RetailPrice__1 retailPrice) {
        this.retailPrice = retailPrice;
    }

}