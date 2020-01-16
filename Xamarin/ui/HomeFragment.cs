using Android.OS;
using Android.Support.V7.Widget;
using Android.Util;
using Android.Views;
using GoogleBooks.Classes;
using GoogleBooks.Network;
using System.Collections.Generic;
using System.Threading.Tasks;
using Xamarin.Forms.Extended;
using static GoogleBooks.Network.Requests;
using System.Linq;
using Android.Content;
using System;
using Android.Widget;
using Newtonsoft.Json;

namespace GoogleBooks
{
    public class HomeFragment : Android.Support.V4.App.Fragment, View.IOnClickListener, OnScrollListener.IScrollStatus, OnBookClickListener
    {
        public string query;

        private RecyclerView recycler;
        private RecyclerViewAdapter adapter;
        private GridLayoutManager layoutManager;

        private List<Book> bookList = new List<Book>();

        private Boolean isFilteredByFavorites = false;

        private View mView;

        private BookSelectedListener bookSelectedListener;

        private string currentSearchQuery = "Android";

        private bool appWasResumed = true;
        public override void OnActivityCreated(Bundle savedInstanceState)
        {
            base.OnActivityCreated(savedInstanceState);
        }

        public override void OnResume()
        {
            base.OnResume();
            appWasResumed = true;
        }

        // UI 

        private EditText searchEditText;
        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            mView = inflater.Inflate(Resource.Layout.fragment_home, container, false);

            ImageView searchImage = (ImageView) mView.FindViewById(Resource.Id.search_button);
            searchImage.SetOnClickListener(this);

            ImageView favoriteImage = (ImageView)mView.FindViewById(Resource.Id.favorite_filter);
            favoriteImage.SetOnClickListener(this);

            searchEditText = (EditText)mView.FindViewById(Resource.Id.search_bar);

            LoadBooks();

            return mView;
        }

        public override void OnAttach(Context context)
        {
            base.OnAttach(context);
            try {
                bookSelectedListener = (BookSelectedListener)Activity; 
            }
            catch (Exception) { }
        }

        private async void LoadBooks()
        {
            try
            {
                Requests requests = new Requests();
                BooksResult result = await requests.GetBooks();
                currentSearchQuery = "Android"; // Default value

                resolvingResult(result);
            } catch (Java.Net.UnknownHostException)
            {
                Toast.MakeText(Activity.ApplicationContext, "No Internet Connection", ToastLength.Long).Show();
            }
            
        }

        public async void LoadBooksByName(string seachQuery)
        {
            try
            {
                Requests requests = new Requests();
                BooksResult result = await requests.GetBooks(seachQuery);

                currentSearchQuery = seachQuery;

                resolvingResult(result, true);
            }
            catch (Java.Net.UnknownHostException)
            {
                Toast.MakeText(Activity.ApplicationContext, "No Internet Connection", ToastLength.Long).Show();
            }
}

        private async void LoadBooksPagination()
        {
            try
            {
                Requests requests = new Requests();
                BooksResult result = await requests.GetBooks(currentSearchQuery);

                resolvingResult(result);
            }
            catch (Java.Net.UnknownHostException)
            {
                Toast.MakeText(Activity.ApplicationContext, "No Internet Connection", ToastLength.Long).Show();
            }
        }

        // Unpacking the result into an array of books.
        private void resolvingResult(BooksResult result, bool isNewQuery = false)
        {
            Book[] books = result.books.ToArray();

            if (appWasResumed)
            {
                appWasResumed = false;
                SetupRecyclerView();
            }


            if (isNewQuery)
            {
                bookList.Clear();
                bookList.AddRange(from Book book in books
                                  select book);
                adapter.AddNewBooks(books);
            } else
            {
                bookList.AddRange(from Book book in books
                                  select book);

                    adapter.AddBooks(books);
                

                
            }
            // This happens every time there is a request
            
            RestInfo.onGoingRequest = false;
            isFilteredByFavorites = false; // If this method was called it means the user searched for something
        }

        private RecyclerView SetupRecyclerView()
        {
            recycler = mView.FindViewById<RecyclerView>(Resource.Id.home_recycler);
            layoutManager = new GridLayoutManager(Activity, 2);
            recycler.HasFixedSize = true;
            recycler.SetLayoutManager(layoutManager);

            OnScrollListener onScrollListener = new OnScrollListener(this, layoutManager);
            recycler.AddOnScrollListener(onScrollListener);
            adapter = new RecyclerViewAdapter(bookList,this);
            recycler.SetAdapter(adapter);

            return recycler;
        }

        public void OnClick(View v)
        {
            switch (v.Id)
            {
                case Resource.Id.search_button:
                    if (!string.IsNullOrEmpty(searchEditText.Text))
                        LoadBooksByName(searchEditText.Text);
                    else
                        LoadBooksByName("Android");
                    break;

                case Resource.Id.favorite_filter:
                    if (!isFilteredByFavorites)
                    {
                        List<Book> favoriteBooks = retrieveFavorites();
                        if (favoriteBooks != null)
                        {
                            if (favoriteBooks.Any())
                            {
                                isFilteredByFavorites = true;
                                adapter.AddNewBooks(favoriteBooks);
                            }
                            else
                            {
                                Toast.MakeText(Activity.ApplicationContext, "No favorite books yet", ToastLength.Long).Show();
                            }
                        }
                        else
                        {
                            Toast.MakeText(Activity.ApplicationContext, "No favorite books yet", ToastLength.Long).Show();
                        }
                    }
                    else
                    {
                        isFilteredByFavorites = false;
                        LoadBooksByName("Android");
                    }
                    break;
            }
        }

        public void MaxScrollReached(int currentItemNumber)
        {
                RestInfo.onGoingRequest = true;
                if (!isFilteredByFavorites)
                    LoadBooksPagination();
            
        }

        public void onBookClick(View itemView, int position, bool isLongClick)
        {
            Book bookSelected = bookList[position];
            bookSelectedListener.OnBookSelected(bookSelected);
        }

        // INTERFACES
        public interface BookSelectedListener
        {
            void OnBookSelected(Book book);
        }


        
        private static List<Book> retrieveFavorites()
        {
            const string FILE_NAME = "favorites.json";
            string documentPath = System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal);
            string path = System.IO.Path.Combine(documentPath, FILE_NAME);

            List<Book> favoriteBooks = null;

            if (System.IO.File.Exists(path))
            {
                string jsondata = System.IO.File.ReadAllText(path);
                Book[] books = JsonConvert.DeserializeObject<Book[]>(jsondata);
                favoriteBooks = new List<Book>(books);
            }
            else
            {
                Log.Debug("DetailBookFragment", "File doesnt exist yet");
            }
            return favoriteBooks;

        }
    }
}