package com.example.wolfbooksapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetailPrice__1 {

    @SerializedName("amountInMicros")
    @Expose
    private Long amountInMicros;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;

    public Long getAmountInMicros() {
        return amountInMicros;
    }

    public void setAmountInMicros(Long amountInMicros) {
        this.amountInMicros = amountInMicros;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}