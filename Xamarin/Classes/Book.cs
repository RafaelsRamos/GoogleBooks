using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Newtonsoft.Json;

namespace GoogleBooks.Classes
{
    public class Book
    {

        [JsonProperty("kind")]
        public string kind { get; set; }

        [JsonProperty("id")]
        public string id { get; set; }

        [JsonProperty("etag")]
        public string etag { get; set; }

        [JsonProperty("selfLink")]
        public string selfLink { get; set; }

        [JsonProperty("volumeInfo")]
        public VolumeInfo volumeInfo { get; set; }

        [JsonProperty("saleInfo")]
        public SaleInfo saleInfo { get; set; }

        [JsonProperty("accessInfo")]
        public AccessInfo accessInfo { get; set; }

        [JsonProperty("searchInfo")]
        public SearchInfo searchInfo { get; set; }

        public string GetThumbnailUrl()
        {
            try
            {
                return volumeInfo.imageLinks.smallThumbnail;
            } catch (Exception ex)
            {
                Log.Debug("MainActivity", ex.Message);
                return null;
            }
        }

        public string GetTitle()
        {
            return volumeInfo.title;
        }

        public IList<string> GetAuthors()
        {
            return volumeInfo.authors;
        }

        public string GetDescription ()
        {
            return volumeInfo.description;
        }

        public string GetBuyUrl()
        {
            return saleInfo.buyLink;
        }
    }

    public class IndustryIdentifier
    {

        [JsonProperty("type")]
        public string type { get; set; }

        [JsonProperty("identifier")]
        public string identifier { get; set; }
    }

    public class ReadingModes
    {

        [JsonProperty("text")]
        public bool text { get; set; }

        [JsonProperty("image")]
        public bool image { get; set; }
    }

    public class PanelizationSummary
    {

        [JsonProperty("containsEpubBubbles")]
        public bool containsEpubBubbles { get; set; }

        [JsonProperty("containsImageBubbles")]
        public bool containsImageBubbles { get; set; }
    }

    public class ImageLinks
    {

        [JsonProperty("smallThumbnail")]
        public string smallThumbnail { get; set; }

        [JsonProperty("thumbnail")]
        public string thumbnail { get; set; }
    }

    public class VolumeInfo
    {

        [JsonProperty("title")]
        public string title { get; set; }

        [JsonProperty("subtitle")]
        public string subtitle { get; set; }

        [JsonProperty("authors")]
        public IList<string> authors { get; set; }

        [JsonProperty("publisher")]
        public string publisher { get; set; }

        [JsonProperty("publishedDate")]
        public string publishedDate { get; set; }

        [JsonProperty("description")]
        public string description { get; set; }

        [JsonProperty("industryIdentifiers")]
        public IList<IndustryIdentifier> industryIdentifiers { get; set; }

        [JsonProperty("readingModes")]
        public ReadingModes readingModes { get; set; }

        [JsonProperty("pageCount")]
        public int pageCount { get; set; }

        [JsonProperty("printType")]
        public string printType { get; set; }

        [JsonProperty("categories")]
        public IList<string> categories { get; set; }

        [JsonProperty("maturityRating")]
        public string maturityRating { get; set; }

        [JsonProperty("allowAnonLogging")]
        public bool allowAnonLogging { get; set; }

        [JsonProperty("contentVersion")]
        public string contentVersion { get; set; }

        [JsonProperty("panelizationSummary")]
        public PanelizationSummary panelizationSummary { get; set; }

        [JsonProperty("imageLinks")]
        public ImageLinks imageLinks { get; set; }

        [JsonProperty("language")]
        public string language { get; set; }

        [JsonProperty("previewLink")]
        public string previewLink { get; set; }

        [JsonProperty("infoLink")]
        public string infoLink { get; set; }

        [JsonProperty("canonicalVolumeLink")]
        public string canonicalVolumeLink { get; set; }

        [JsonProperty("averageRating")]
        public double? averageRating { get; set; }

        [JsonProperty("ratingsCount")]
        public int? ratingsCount { get; set; }
    }

    public class ListPrice
    {

        [JsonProperty("amount")]
        public double amount { get; set; }

        [JsonProperty("currencyCode")]
        public string currencyCode { get; set; }
    }

    public class RetailPrice
    {

        [JsonProperty("amount")]
        public double amount { get; set; }

        [JsonProperty("currencyCode")]
        public string currencyCode { get; set; }
    }

    public class Offer
    {

        [JsonProperty("finskyOfferType")]
        public int finskyOfferType { get; set; }

        [JsonProperty("listPrice")]
        public ListPrice listPrice { get; set; }

        [JsonProperty("retailPrice")]
        public RetailPrice retailPrice { get; set; }
    }

    public class SaleInfo
    {

        [JsonProperty("country")]
        public string country { get; set; }

        [JsonProperty("saleability")]
        public string saleability { get; set; }

        [JsonProperty("isEbook")]
        public bool isEbook { get; set; }

        [JsonProperty("listPrice")]
        public ListPrice listPrice { get; set; }

        [JsonProperty("retailPrice")]
        public RetailPrice retailPrice { get; set; }

        [JsonProperty("buyLink")]
        public string buyLink { get; set; }

        [JsonProperty("offers")]
        public IList<Offer> offers { get; set; }
    }

    public class Epub
    {

        [JsonProperty("isAvailable")]
        public bool isAvailable { get; set; }
    }

    public class Pdf
    {

        [JsonProperty("isAvailable")]
        public bool isAvailable { get; set; }

        [JsonProperty("acsTokenLink")]
        public string acsTokenLink { get; set; }
    }

    public class AccessInfo
    {

        [JsonProperty("country")]
        public string country { get; set; }

        [JsonProperty("viewability")]
        public string viewability { get; set; }

        [JsonProperty("embeddable")]
        public bool embeddable { get; set; }

        [JsonProperty("publicDomain")]
        public bool publicDomain { get; set; }

        [JsonProperty("textToSpeechPermission")]
        public string textToSpeechPermission { get; set; }

        [JsonProperty("epub")]
        public Epub epub { get; set; }

        [JsonProperty("pdf")]
        public Pdf pdf { get; set; }

        [JsonProperty("webReaderLink")]
        public string webReaderLink { get; set; }

        [JsonProperty("accessViewStatus")]
        public string accessViewStatus { get; set; }

        [JsonProperty("quoteSharingAllowed")]
        public bool quoteSharingAllowed { get; set; }
    }

    public class SearchInfo
    {

        [JsonProperty("textSnippet")]
        public string textSnippet { get; set; }
    }
}
