package com.example.fyldproject.data.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class Book implements Parcelable {

    @Expose
    @SerializedName("searchInfo")
    private SearchInfo searchInfo;
    @Expose
    @SerializedName("accessInfo")
    private AccessInfo accessInfo;
    @Expose
    @SerializedName("saleInfo")
    private SaleInfo saleInfo;
    @Expose
    @SerializedName("volumeInfo")
    private VolumeInfo volumeInfo;
    @Expose
    @SerializedName("selfLink")
    private String selfLink;
    @Expose
    @SerializedName("etag")
    private String etag;
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("kind")
    private String kind;

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public static class SearchInfo {
        @Expose
        @SerializedName("textSnippet")
        private String textSnippet;

        public String getTextSnippet() {
            return textSnippet;
        }

        public void setTextSnippet(String textSnippet) {
            this.textSnippet = textSnippet;
        }
    }

    public static class AccessInfo {
        @Expose
        @SerializedName("quoteSharingAllowed")
        private boolean quoteSharingAllowed;
        @Expose
        @SerializedName("accessViewStatus")
        private String accessViewStatus;
        @Expose
        @SerializedName("webReaderLink")
        private String webReaderLink;
        @Expose
        @SerializedName("pdf")
        private Pdf pdf;
        @Expose
        @SerializedName("epub")
        private Epub epub;
        @Expose
        @SerializedName("textToSpeechPermission")
        private String textToSpeechPermission;
        @Expose
        @SerializedName("publicDomain")
        private boolean publicDomain;
        @Expose
        @SerializedName("embeddable")
        private boolean embeddable;
        @Expose
        @SerializedName("viewability")
        private String viewability;
        @Expose
        @SerializedName("country")
        private String country;

        public boolean getQuoteSharingAllowed() {
            return quoteSharingAllowed;
        }

        public void setQuoteSharingAllowed(boolean quoteSharingAllowed) {
            this.quoteSharingAllowed = quoteSharingAllowed;
        }

        public String getAccessViewStatus() {
            return accessViewStatus;
        }

        public void setAccessViewStatus(String accessViewStatus) {
            this.accessViewStatus = accessViewStatus;
        }

        public String getWebReaderLink() {
            return webReaderLink;
        }

        public void setWebReaderLink(String webReaderLink) {
            this.webReaderLink = webReaderLink;
        }

        public Pdf getPdf() {
            return pdf;
        }

        public void setPdf(Pdf pdf) {
            this.pdf = pdf;
        }

        public Epub getEpub() {
            return epub;
        }

        public void setEpub(Epub epub) {
            this.epub = epub;
        }

        public String getTextToSpeechPermission() {
            return textToSpeechPermission;
        }

        public void setTextToSpeechPermission(String textToSpeechPermission) {
            this.textToSpeechPermission = textToSpeechPermission;
        }

        public boolean getPublicDomain() {
            return publicDomain;
        }

        public void setPublicDomain(boolean publicDomain) {
            this.publicDomain = publicDomain;
        }

        public boolean getEmbeddable() {
            return embeddable;
        }

        public void setEmbeddable(boolean embeddable) {
            this.embeddable = embeddable;
        }

        public String getViewability() {
            return viewability;
        }

        public void setViewability(String viewability) {
            this.viewability = viewability;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public static class Pdf {
        @Expose
        @SerializedName("acsTokenLink")
        private String acsTokenLink;
        @Expose
        @SerializedName("isAvailable")
        private boolean isAvailable;

        public String getAcsTokenLink() {
            return acsTokenLink;
        }

        public void setAcsTokenLink(String acsTokenLink) {
            this.acsTokenLink = acsTokenLink;
        }

        public boolean getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
        }
    }

    public static class Epub {
        @Expose
        @SerializedName("acsTokenLink")
        private String acsTokenLink;
        @Expose
        @SerializedName("isAvailable")
        private boolean isAvailable;

        public String getAcsTokenLink() {
            return acsTokenLink;
        }

        public void setAcsTokenLink(String acsTokenLink) {
            this.acsTokenLink = acsTokenLink;
        }

        public boolean getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
        }
    }

    public static class SaleInfo {
        @Expose
        @SerializedName("offers")
        private List<Offers> offers;
        @Expose
        @SerializedName("buyLink")
        private String buyLink;
        @Expose
        @SerializedName("retailPrice")
        private RetailPrice retailPrice;
        @Expose
        @SerializedName("listPrice")
        private ListPrice listPrice;
        @Expose
        @SerializedName("isEbook")
        private boolean isEbook;
        @Expose
        @SerializedName("saleability")
        private String saleability;
        @Expose
        @SerializedName("country")
        private String country;

        public List<Offers> getOffers() {
            return offers;
        }

        public void setOffers(List<Offers> offers) {
            this.offers = offers;
        }

        public String getBuyLink() {
            return buyLink;
        }

        public void setBuyLink(String buyLink) {
            this.buyLink = buyLink;
        }

        public RetailPrice getRetailPrice() {
            return retailPrice;
        }

        public void setRetailPrice(RetailPrice retailPrice) {
            this.retailPrice = retailPrice;
        }

        public ListPrice getListPrice() {
            return listPrice;
        }

        public void setListPrice(ListPrice listPrice) {
            this.listPrice = listPrice;
        }

        public boolean getIsEbook() {
            return isEbook;
        }

        public void setIsEbook(boolean isEbook) {
            this.isEbook = isEbook;
        }

        public String getSaleability() {
            return saleability;
        }

        public void setSaleability(String saleability) {
            this.saleability = saleability;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public static class Offers {
        @Expose
        @SerializedName("retailPrice")
        private RetailPriceMicros retailPrice;
        @Expose
        @SerializedName("listPrice")
        private ListPriceMicros listPrice;
        @Expose
        @SerializedName("finskyOfferType")
        private int finskyOfferType;

        public RetailPriceMicros getRetailPrice() {
            return retailPrice;
        }

        public void setRetailPrice(RetailPriceMicros retailPrice) {
            this.retailPrice = retailPrice;
        }

        public ListPriceMicros getListPrice() {
            return listPrice;
        }

        public void setListPrice(ListPriceMicros listPrice) {
            this.listPrice = listPrice;
        }

        public int getFinskyOfferType() {
            return finskyOfferType;
        }

        public void setFinskyOfferType(int finskyOfferType) {
            this.finskyOfferType = finskyOfferType;
        }
    }

    public static class RetailPriceMicros {
        @Expose
        @SerializedName("currencyCode")
        private String currencyCode;
        @Expose
        @SerializedName("amountInMicros")
        private int amountInMicros;

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public int getAmountInMicros() {
            return amountInMicros;
        }

        public void setAmountInMicros(int amountInMicros) {
            this.amountInMicros = amountInMicros;
        }
    }

    public static class ListPriceMicros {
        @Expose
        @SerializedName("currencyCode")
        private String currencyCode;
        @Expose
        @SerializedName("amountInMicros")
        private int amountInMicros;

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public int getAmountInMicros() {
            return amountInMicros;
        }

        public void setAmountInMicros(int amountInMicros) {
            this.amountInMicros = amountInMicros;
        }
    }

    public static class RetailPrice {
        @Expose
        @SerializedName("currencyCode")
        private String currencyCode;
        @Expose
        @SerializedName("amount")
        private double amount;

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    public static class ListPrice {
        @Expose
        @SerializedName("currencyCode")
        private String currencyCode;
        @Expose
        @SerializedName("amount")
        private double amount;

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    public static class VolumeInfo {
        @Expose
        @SerializedName("canonicalVolumeLink")
        private String canonicalVolumeLink;
        @Expose
        @SerializedName("infoLink")
        private String infoLink;
        @Expose
        @SerializedName("previewLink")
        private String previewLink;
        @Expose
        @SerializedName("language")
        private String language;
        @Expose
        @SerializedName("imageLinks")
        private ImageLinks imageLinks;
        @Expose
        @SerializedName("panelizationSummary")
        private PanelizationSummary panelizationSummary;
        @Expose
        @SerializedName("contentVersion")
        private String contentVersion;
        @Expose
        @SerializedName("allowAnonLogging")
        private boolean allowAnonLogging;
        @Expose
        @SerializedName("maturityRating")
        private String maturityRating;
        @Expose
        @SerializedName("categories")
        private List<String> categories;
        @Expose
        @SerializedName("printType")
        private String printType;
        @Expose
        @SerializedName("pageCount")
        private int pageCount;
        @Expose
        @SerializedName("readingModes")
        private ReadingModes readingModes;
        @Expose
        @SerializedName("industryIdentifiers")
        private List<IndustryIdentifiers> industryIdentifiers;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("publishedDate")
        private String publishedDate;
        @Expose
        @SerializedName("publisher")
        private String publisher;
        @Expose
        @SerializedName("authors")
        private List<String> authors;
        @Expose
        @SerializedName("subtitle")
        private String subtitle;
        @Expose
        @SerializedName("title")
        private String title;

        public String getCanonicalVolumeLink() {
            return canonicalVolumeLink;
        }

        public void setCanonicalVolumeLink(String canonicalVolumeLink) {
            this.canonicalVolumeLink = canonicalVolumeLink;
        }

        public String getInfoLink() {
            return infoLink;
        }

        public void setInfoLink(String infoLink) {
            this.infoLink = infoLink;
        }

        public String getPreviewLink() {
            return previewLink;
        }

        public void setPreviewLink(String previewLink) {
            this.previewLink = previewLink;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public ImageLinks getImageLinks() {
            return imageLinks;
        }

        public void setImageLinks(ImageLinks imageLinks) {
            this.imageLinks = imageLinks;
        }

        public PanelizationSummary getPanelizationSummary() {
            return panelizationSummary;
        }

        public void setPanelizationSummary(PanelizationSummary panelizationSummary) {
            this.panelizationSummary = panelizationSummary;
        }

        public String getContentVersion() {
            return contentVersion;
        }

        public void setContentVersion(String contentVersion) {
            this.contentVersion = contentVersion;
        }

        public boolean getAllowAnonLogging() {
            return allowAnonLogging;
        }

        public void setAllowAnonLogging(boolean allowAnonLogging) {
            this.allowAnonLogging = allowAnonLogging;
        }

        public String getMaturityRating() {
            return maturityRating;
        }

        public void setMaturityRating(String maturityRating) {
            this.maturityRating = maturityRating;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public String getPrintType() {
            return printType;
        }

        public void setPrintType(String printType) {
            this.printType = printType;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public ReadingModes getReadingModes() {
            return readingModes;
        }

        public void setReadingModes(ReadingModes readingModes) {
            this.readingModes = readingModes;
        }

        public List<IndustryIdentifiers> getIndustryIdentifiers() {
            return industryIdentifiers;
        }

        public void setIndustryIdentifiers(List<IndustryIdentifiers> industryIdentifiers) {
            this.industryIdentifiers = industryIdentifiers;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public List<String> getAuthors() {
            return authors;
        }

        public void setAuthors(List<String> authors) {
            this.authors = authors;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ImageLinks {
        @Expose
        @SerializedName("thumbnail")
        private String thumbnail;
        @Expose
        @SerializedName("smallThumbnail")
        private String smallThumbnail;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getSmallThumbnail() {
            return smallThumbnail;
        }

        public void setSmallThumbnail(String smallThumbnail) {
            this.smallThumbnail = smallThumbnail;
        }
    }

    public static class PanelizationSummary {
        @Expose
        @SerializedName("containsImageBubbles")
        private boolean containsImageBubbles;
        @Expose
        @SerializedName("containsEpubBubbles")
        private boolean containsEpubBubbles;

        public boolean getContainsImageBubbles() {
            return containsImageBubbles;
        }

        public void setContainsImageBubbles(boolean containsImageBubbles) {
            this.containsImageBubbles = containsImageBubbles;
        }

        public boolean getContainsEpubBubbles() {
            return containsEpubBubbles;
        }

        public void setContainsEpubBubbles(boolean containsEpubBubbles) {
            this.containsEpubBubbles = containsEpubBubbles;
        }
    }

    public static class ReadingModes {
        @Expose
        @SerializedName("image")
        private boolean image;
        @Expose
        @SerializedName("text")
        private boolean text;

        public boolean getImage() {
            return image;
        }

        public void setImage(boolean image) {
            this.image = image;
        }

        public boolean getText() {
            return text;
        }

        public void setText(boolean text) {
            this.text = text;
        }
    }

    public static class IndustryIdentifiers {
        @Expose
        @SerializedName("identifier")
        private String identifier;
        @Expose
        @SerializedName("type")
        private String type;

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
