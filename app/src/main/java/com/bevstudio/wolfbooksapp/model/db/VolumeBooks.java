package com.bevstudio.wolfbooksapp.model.db;

public class VolumeBooks {

    private int id;
    private String str_id;
    private String volumeId;
    private boolean isBookmark;

    public VolumeBooks() {
    }


    public VolumeBooks(String volumeId, boolean isBookmark) {
        this.volumeId = volumeId;
        this.isBookmark = isBookmark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStr_id() {
        return str_id;
    }

    public void setStr_id(String str_id) {
        this.str_id = str_id;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public boolean isBookmark() {
        return isBookmark;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }
}
