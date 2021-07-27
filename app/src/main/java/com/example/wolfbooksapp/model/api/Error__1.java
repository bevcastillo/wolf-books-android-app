package com.example.wolfbooksapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error__1 {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("locationType")
    @Expose
    private String locationType;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

}