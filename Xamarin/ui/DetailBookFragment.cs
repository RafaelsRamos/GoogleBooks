
using Android.App;
using Android.Graphics;
using Android.OS;
using Android.Views;
using Android.Widget;
using GoogleBooks.Classes;
using System;
using System.Collections.Generic;
using System.Net;
using System.IO;
using Android.Content.Res;
using Java.IO;
using Newtonsoft.Json;
using Android.Util;
using Android.Content;

namespace GoogleBooks
{
    class DetailBookFragment: Android.Support.V4.App.Fragment, View.IOnClickListener
    {
        private Book bookSelected;
        private Button buyButton;
        private ImageView favoriteImageView;

        private static List<Book> favoriteBooks = new List<Book>();
        private bool isBookInFavorites = false;
        private const string FILE_NAME = "favorites.json";

        static string path;
        static string documentPath;

        public override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            HomeActivity activityInstance = (HomeActivity) Activity;
            Xamarin.Forms.Forms.Init(activityInstance, savedInstanceState);
            bookSelected = activityInstance.GetBookSelected();


            documentPath = System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal);
            path = System.IO.Path.Combine(documentPath, FILE_NAME);
        }

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View v = inflater.Inflate(Resource.Layout.fragment_detail_book, container, false);
            buyButton = (Button) v.FindViewById(Resource.Id.detail_view_buy_button);
            buyButton.SetOnClickListener(this);

            favoriteImageView = (ImageView)v.FindViewById(Resource.Id.favorite_image_view);
            favoriteImageView.SetOnClickListener(this);

            checkBookFavorites();

            setUIElements(v);

            return v;
        }

        private void setUIElements(View v)
        {
            // Set image
            ImageView thumbnail = (ImageView) v.FindViewById(Resource.Id.detail_view_thumbnail);
            if (bookSelected.GetThumbnailUrl() != null)
            {
                Bitmap image = GetImageBitmapFromUrl(bookSelected.GetThumbnailUrl());
                thumbnail.SetImageBitmap(image);
            }

            // set title
            TextView title = (TextView) v.FindViewById(Resource.Id.detail_view_title);
            title.Text = bookSelected.GetTitle();

            // set authors
            TextView authors = (TextView)v.FindViewById(Resource.Id.detail_view_authors);
            authors.Text = string.Join(", ", bookSelected.GetAuthors());

            // set description
            TextView description = (TextView)v.FindViewById(Resource.Id.detail_view_description);
            description.Text = bookSelected.GetDescription();

            // set favorite imageview image
            setFavoriteImage();
        }

        private void setFavoriteImage()
        {
            if (isBookInFavorites)
                favoriteImageView.SetImageResource(Resource.Mipmap.favorite);
            else
                favoriteImageView.SetImageResource(Resource.Mipmap.not_favorite);
        }

        private void checkBookFavorites()
        {
            retrieveFavorites();
            foreach (Book book in favoriteBooks)
            {
                Log.Debug("BOOK DETAIL", book.id);
                if (book.id == bookSelected.id)
                    isBookInFavorites = true;
            }
        }

        public static void retrieveFavorites()
        {
            if (System.IO.File.Exists(path))
            {
                string jsondata = System.IO.File.ReadAllText(path);
                Log.Debug("Favorite: ", jsondata);
                Book[] books = JsonConvert.DeserializeObject<Book[]>(jsondata);
                favoriteBooks = new List<Book>(books);
            }
            else
            {
                Log.Debug("DetailBookFragment", "File doesnt exist yet");
            }
        }

        private void sendBookToFavorites()
        {
            favoriteBooks.Add(bookSelected);

            JsonSerializer serializer = new JsonSerializer();
            serializer.NullValueHandling = NullValueHandling.Ignore;

            string jsonStr = JsonConvert.SerializeObject(favoriteBooks);

            WriteFile(path, jsonStr);

            Toast.MakeText(Activity.ApplicationContext, "Book added to favorites", ToastLength.Long).Show();
        }

        private void removeBookFromFavorites()
        {
            for (int i = 0; i < favoriteBooks.Count; i++)
            {
                if (favoriteBooks[i].id == bookSelected.id)
                    favoriteBooks.RemoveAt(i);
            }
            foreach (Book bk in favoriteBooks)
            {
                Log.Debug("Favorite: ", bk.id);
            }

            JsonSerializer serializer = new JsonSerializer();
            serializer.NullValueHandling = NullValueHandling.Ignore;

            string jsonString = JsonConvert.SerializeObject(favoriteBooks);

            WriteFile(path, jsonString);

            Toast.MakeText(Activity.ApplicationContext, "Book Removed from favorites", ToastLength.Long).Show();
        }

        private void WriteFile(string path, string content)
        {
            using (var writer = System.IO.File.CreateText(path))
            {
                writer.Write(content);
            }
            Log.Debug("Favorite: ", "File Created");

            if (System.IO.File.Exists(path))
            {
                string jsondata = System.IO.File.ReadAllText(path);
                Log.Debug("Favorite: ", jsondata);
            }
            /*using (StreamWriter sw = new StreamWriter(path, true))
            {
                sw.Write(content);
            }*/
        }


            private Book[] ReadFile(string path)
        {
            Book[] data;

            if (System.IO.File.Exists(path))
            {
                string jsondata = System.IO.File.ReadAllText(path);
                data = JsonConvert.DeserializeObject<Book[]>(jsondata);
                return data;
            }

            return null;
            
        }

        public void OnClick(View v)
        {
            switch (v.Id)
            {
                case Resource.Id.detail_view_buy_button:
                    OpenBuyLink();
                    break;
                case Resource.Id.favorite_image_view:
                    if (!isBookInFavorites)
                    {
                        sendBookToFavorites();
                        isBookInFavorites = true;
                    }
                    else
                    {
                        isBookInFavorites = false;
                        removeBookFromFavorites();
                    }
                    setFavoriteImage();
                    break;
            }
        }
                     
        private void OpenBuyLink()
        {
            try
            {
                Xamarin.Forms.Device.OpenUri(new Uri(bookSelected.GetBuyUrl()));
            }
            catch (ArgumentNullException)
            {
                Toast.MakeText(Activity.ApplicationContext, "Book link unavailable", ToastLength.Long).Show();
            }
        }

        private Bitmap GetImageBitmapFromUrl(string url)
        {
            Bitmap imageBitmap = null;

            using (var webClient = new WebClient())
            {
                var imageBytes = webClient.DownloadData(url);
                if (imageBytes != null && imageBytes.Length > 0)
                {
                    imageBitmap = BitmapFactory.DecodeByteArray(imageBytes, 0, imageBytes.Length);
                }
            }

            return imageBitmap;
        }


    }
}