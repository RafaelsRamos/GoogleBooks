using Android.Graphics;
using Android.Support.V7.Widget;
using Android.Views;
using Android.Widget;
using System.Collections.Generic;
using System.Net;

namespace GoogleBooks.Classes
{
    class RecyclerViewHolder: RecyclerView.ViewHolder, View.IOnClickListener
    {
        public ImageView thumbnail;
        public TextView title;
        private OnBookClickListener mOnBookClickListener;

        public RecyclerViewHolder(View itemView, OnBookClickListener onBookClickListener):base(itemView)
        {
            mOnBookClickListener = onBookClickListener;
            thumbnail = itemView.FindViewById<ImageView>(Resource.Id.book_thumbnail);
            title = itemView.FindViewById<TextView>(Resource.Id.book_title);
            itemView.SetOnClickListener(this);
        }

        public void OnClick(View v)
        {
            mOnBookClickListener.onBookClick(v, AdapterPosition, false);
        }
    }
    public class RecyclerViewAdapter : RecyclerView.Adapter 
    {
        OnBookClickListener mOnBookClickListener;

        private List<Book> bookList = new List<Book>();

        public RecyclerViewAdapter(List<Book> bookList, OnBookClickListener onBookClickListener)
        {
            mOnBookClickListener = onBookClickListener;
            this.bookList = bookList;
        }

        public void AddBooks(Book[] books)
        {
            bookList.AddRange(books);
            NotifyDataSetChanged();
        }

        public void AddNewBooks(Book[] book)
        {
            bookList.Clear();
            bookList.AddRange(book);
            NotifyDataSetChanged();
        }
        public void AddNewBooks(List<Book> book)
        {
            bookList.Clear();
            bookList.AddRange(book);
            NotifyDataSetChanged();
        }

        public override int ItemCount => bookList.Count;

        public override void OnBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            Book book = bookList[position];
            RecyclerViewHolder viewHolder = holder as RecyclerViewHolder;
            if (book.GetThumbnailUrl() != null)
            {
                Bitmap image = GetImageBitmapFromUrl(book.GetThumbnailUrl());
                viewHolder.thumbnail.SetImageBitmap(image);
            }
            viewHolder.title.Text = book.GetTitle();
        }

        public override RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.From(parent.Context);
            View itemView = inflater.Inflate(Resource.Layout.item_menu, parent, false);
            return new RecyclerViewHolder(itemView, mOnBookClickListener);
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

    public interface OnBookClickListener
    {
        void onBookClick(View itemView, int position, bool isLongClick);
    }
}