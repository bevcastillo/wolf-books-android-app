package com.example.bookfinderapp.modelV2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPrice__1 {

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