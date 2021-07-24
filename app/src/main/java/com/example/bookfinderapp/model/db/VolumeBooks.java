package com.example.bookfinderapp.models;

public class VolumeBooks {

    private int id;
    private String str_id;
    private String volumeId;
    private String title;
    private String authors;
    private String description;
    private String publisher;
    private String publishedDate;
    private String categories;
    private String thumbnail;
    private String previewLink;
    private String price;
    private String currencyCode;
    private String buyLink;
    private String language;
    private int pageCount;
    private double averageRating;
    private int ratingsCount;
    private boolean isBookmark;

    public VolumeBooks() {
    }

//    public VolumeBooks(String title, String subtitle, String authors, String description, String publisher, String publishedDate, String categories, String thumbnail, String previewLink, String price, String currencyCode, String buyLink, String language, int pageCount, double averageRating, int ratingsCount, boolean isBookmark) {
//    }


    public VolumeBooks(String volumeId, boolean isBookmark) {
        this.volumeId = volumeId;
        this.isBookmark = isBookmark;
    }

    public VolumeBooks(String str_id, String volumeId, String title, String authors, String description,
                       String publisher, String publishedDate, String categories, String thumbnail,
                       String previewLink, String price, String currencyCode, String buyLink,
                       String language, int pageCount, double averageRating, int ratingsCount, boolean isBookmark) {
        this.str_id = str_id;
        this.volumeId = volumeId;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.categories = categories;
        this.thumbnail = thumbnail;
        this.previewLink = previewLink;
        this.price = price;
        this.currencyCode = currencyCode;
        this.buyLink = buyLink;
        this.language = language;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.isBookmark = isBookmark;
    }

    public VolumeBooks(String volumeId, String title, String authors,
                       String description, String publisher, String publishedDate,
                       String categories, String thumbnail, String previewLink,
                       String price, String currencyCode,
                       String buyLink, String language, int pageCount,
                       double averageRating, int ratingsCount, boolean isBookmark) {
        this.volumeId = volumeId;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.categories = categories;
        this.thumbnail = thumbnail;
        this.previewLink = previewLink;
        this.price = price;
        this.currencyCode = currencyCode;
        this.buyLink = buyLink;
        this.language = language;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.isBookmark = isBookmark;
    }

    public int getId() {
        return id;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStr_id(String str_id) {
        this.str_id = str_id;
    }

    public String getTitle() {
        return title;
    }

    public String getStr_id() {
        return str_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRatings) {
        this.averageRating = averageRatings;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public boolean isBookmark() {
        return isBookmark;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }
}
