package com.example.bookfinderapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BooksVolume{

    @SerializedName("kind")
    public String kind;
    @SerializedName("id")
    public String id;
    @SerializedName("etag")
    public String etag;
    @SerializedName("selfLink")
    public String selfLink;
    @SerializedName("volumeInfo")
    public VolumeInfoBean volumeInfo;
    @SerializedName("saleInfo")
    public SaleInfoBean saleInfo;
    @SerializedName("accessInfo")
    public AccessInfoBean accessInfo;
    @SerializedName("searchInfo")
    public SearchInfoBean searchInfo;

//    @Override
//    public String toString() {
//        return convertToString(this);
//    }
//
//    @Override
//    public BooksVolume convertFromJson(String json) {
//        return convertFromJson(json, BooksVolume.class);
//    }

    public static class VolumeInfoBean {
        @SerializedName("title")
        public String title;
        @SerializedName("subtitle")
        public String subtitle;
        @SerializedName("publisher")
        public String publisher;
        @SerializedName("publishedDate")
        public String publishedDate;
        @SerializedName("description")
        public String description;
        @SerializedName("readingModes")
        public ReadingModesBean readingModes;
        @SerializedName("pageCount")
        public int pageCount;
        @SerializedName("printType")
        public String printType;
        @SerializedName("averageRating")
        public int averageRating;
        @SerializedName("ratingsCount")
        public int ratingsCount;
        @SerializedName("maturityRating")
        public String maturityRating;
        @SerializedName("allowAnonLogging")
        public boolean allowAnonLogging;
        @SerializedName("contentVersion")
        public String contentVersion;
        @SerializedName("panelizationSummary")
        public PanelizationSummaryBean panelizationSummary;
        @SerializedName("imageLinks")
        public ImageLinksBean imageLinks;
        @SerializedName("language")
        public String language;
        @SerializedName("previewLink")
        public String previewLink;
        @SerializedName("infoLink")
        public String infoLink;
        @SerializedName("canonicalVolumeLink")
        public String canonicalVolumeLink;
        @SerializedName("authors")
        public List<String> authors;
        @SerializedName("industryIdentifiers")
        public List<IndustryIdentifiersBean> industryIdentifiers;
        @SerializedName("categories")
        public List<String> categories;

        public static class ReadingModesBean {
            @SerializedName("text")
            public boolean text;
            @SerializedName("image")
            public boolean image;
        }

        public static class PanelizationSummaryBean {
            @SerializedName("containsEpubBubbles")
            public boolean containsEpubBubbles;
            @SerializedName("containsImageBubbles")
            public boolean containsImageBubbles;
        }

        public static class ImageLinksBean {
            @SerializedName("smallThumbnail")
            public String smallThumbnail;
            @SerializedName("thumbnail")
            public String thumbnail;
        }

        public static class IndustryIdentifiersBean {
            @SerializedName("type")
            public String type;
            @SerializedName("identifier")
            public String identifier;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getPublisher() {
            return publisher;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public String getDescription() {
            return description;
        }

        public ReadingModesBean getReadingModes() {
            return readingModes;
        }

        public int getPageCount() {
            return pageCount;
        }

        public String getPrintType() {
            return printType;
        }

        public int getAverageRating() {
            return averageRating;
        }

        public int getRatingsCount() {
            return ratingsCount;
        }

        public String getMaturityRating() {
            return maturityRating;
        }

        public boolean isAllowAnonLogging() {
            return allowAnonLogging;
        }

        public String getContentVersion() {
            return contentVersion;
        }

        public PanelizationSummaryBean getPanelizationSummary() {
            return panelizationSummary;
        }

        public ImageLinksBean getImageLinks() {
            return imageLinks;
        }

        public String getLanguage() {
            return language;
        }

        public String getPreviewLink() {
            return previewLink;
        }

        public String getInfoLink() {
            return infoLink;
        }

        public String getCanonicalVolumeLink() {
            return canonicalVolumeLink;
        }

        public List<String> getAuthors() {
            return authors;
        }

        public List<IndustryIdentifiersBean> getIndustryIdentifiers() {
            return industryIdentifiers;
        }

        public List<String> getCategories() {
            return categories;
        }
    }

    public static class SaleInfoBean {
        @SerializedName("country")
        public String country;
        @SerializedName("saleability")
        public String saleability;
        @SerializedName("isEbook")
        public boolean isEbook;
    }

    public static class AccessInfoBean {
        @SerializedName("country")
        public String country;
        @SerializedName("viewability")
        public String viewability;
        @SerializedName("embeddable")
        public boolean embeddable;
        @SerializedName("publicDomain")
        public boolean publicDomain;
        @SerializedName("textToSpeechPermission")
        public String textToSpeechPermission;
        @SerializedName("epub")
        public EpubBean epub;
        @SerializedName("pdf")
        public PdfBean pdf;
        @SerializedName("webReaderLink")
        public String webReaderLink;
        @SerializedName("accessViewStatus")
        public String accessViewStatus;
        @SerializedName("quoteSharingAllowed")
        public boolean quoteSharingAllowed;

        public static class EpubBean {
            @SerializedName("isAvailable")
            public boolean isAvailable;
            @SerializedName("acsTokenLink")
            public String acsTokenLink;
        }

        public static class PdfBean {
            @SerializedName("isAvailable")
            public boolean isAvailable;
            @SerializedName("acsTokenLink")
            public String acsTokenLink;
        }
    }

    public static class SearchInfoBean {
        @SerializedName("textSnippet")
        public String textSnippet;
    }

    public String getKind() {
        return kind;
    }

    public String getId() {
        return id;
    }

    public String getEtag() {
        return etag;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public VolumeInfoBean getVolumeInfo() {
        return volumeInfo;
    }

    public SaleInfoBean getSaleInfo() {
        return saleInfo;
    }

    public AccessInfoBean getAccessInfo() {
        return accessInfo;
    }

    public SearchInfoBean getSearchInfo() {
        return searchInfo;
    }

}
