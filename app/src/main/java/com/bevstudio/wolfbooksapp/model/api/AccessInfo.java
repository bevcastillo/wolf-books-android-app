package com.bevstudio.wolfbooksapp.model.api;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessInfo {
    @SerializedName("webReaderLink")
    @Expose
    private String webReaderLink;

    public String getWebReaderLink() {
        return webReaderLink;
    }
}