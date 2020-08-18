package com.example.bookfinderapp.models;

public class VolumeBooks {

    private String title;
    private String subtitle;
    private String authors;
    private String description;
    private String publisher;
    private String publishedDate;
    private String categories;
    private String thumbnail;
    private String previewLink;
    private String infoLink;
    private String price;
    private String currencyCode;
    private String buyLink;
    private String language;
    private String isbn;
    private int pageCount;
    private int averageRating;

    public VolumeBooks() {
    }

    public VolumeBooks(String title, String subtitle, String authors,
                       String description, String publisher, String publishedDate,
                       String categories, String thumbnail, String previewLink,
                       String infoLink, String price, String currencyCode,
                       String buyLink, String language, String isbn, int pageCount, int averageRating) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.description = description;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.categories = categories;
        this.thumbnail = thumbnail;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
        this.price = price;
        this.currencyCode = currencyCode;
        this.buyLink = buyLink;
        this.language = language;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRatings) {
        this.averageRating = averageRatings;
    }
}
