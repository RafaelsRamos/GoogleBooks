using System;

using Android.App;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Support.V4.App;
using GoogleBooks.Classes;

namespace GoogleBooks
{
    [Activity(Label = "@string/app_name", Theme = "@style/AppTheme", MainLauncher = true)]
    public class HomeActivity : FragmentActivity, HomeFragment.BookSelectedListener
    {

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            Xamarin.Essentials.Platform.Init(this, savedInstanceState);
            // Set our view from the "main" layout resource
            try
            {
                SetContentView(Resource.Layout.activity_home);
            }
            catch (Exception ex)
            {
                Log.Debug("MainActivity", ex.Message);
            }

            init();
        }

        public override void OnRequestPermissionsResult(int requestCode, string[] permissions, [GeneratedEnum] Android.Content.PM.Permission[] grantResults)
        {
            Xamarin.Essentials.Platform.OnRequestPermissionsResult(requestCode, permissions, grantResults);

            base.OnRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        // Initializing the fragment
        public void init()
        {
            Android.Support.V4.App.FragmentManager fragmentManager = SupportFragmentManager;
            
            HomeFragment homeFragment = new HomeFragment();
            _ = fragmentManager.BeginTransaction().Replace(Resource.Id.home_frame_layout, homeFragment).Commit();
        }

        public void OpenDetailedFragment()
        {
            Android.Support.V4.App.FragmentManager fragmentManager = SupportFragmentManager;

            DetailBookFragment detailBookFragment = new DetailBookFragment();
            _ = fragmentManager.BeginTransaction()
                .Replace(Resource.Id.home_frame_layout, detailBookFragment)
                .AddToBackStack(null)
                .Commit();
        }

        private Book bookSelected;
        public void OnBookSelected(Book book)
        {
            bookSelected = book;
            OpenDetailedFragment();
        }

        public Book GetBookSelected()
        {
            return bookSelected;
        }
    }
}