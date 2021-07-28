package com.bevstudio.wolfbooksapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dimensions {

    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("thickness")
    @Expose
    private String thickness;

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public String getThickness() {
        return thickness;
    }
}