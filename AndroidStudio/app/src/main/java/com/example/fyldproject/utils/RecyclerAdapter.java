package com.example.fyldproject.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.example.fyldproject.R;
import com.example.fyldproject.data.model.Book;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Book> books;
    private Context context;

    private OnBookClickListener mOnBookClickListener;

    public RecyclerAdapter(List<Book> booksList, Context context, OnBookClickListener onBookClickListener ) {
        this.books = booksList;
        this.context = context;

        this.mOnBookClickListener = onBookClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MyViewHolder(view, mOnBookClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = books.get(position);
        holder.title.setText(book.getTitle());

        System.out.println(book);
        if (book.getImageThumbnail() != null) {
            GlideUrl glideUrl = new GlideUrl(book.getImageThumbnail());
            Glide.with(context).load(glideUrl).into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumbnail;
        TextView title;
        OnBookClickListener onBookClickListener;

        MyViewHolder(View itemView, OnBookClickListener onBookClickListener) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.book_thumbnail);
            title = itemView.findViewById(R.id.book_title);
            this.onBookClickListener = onBookClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onBookClickListener.onBookClick(getAdapterPosition());
        }
    }

    public void addBooks(List<Book> books) {
        this.books.addAll(books);
        notifyDataSetChanged();
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public interface OnBookClickListener {
        void onBookClick(int position);
    }
}
