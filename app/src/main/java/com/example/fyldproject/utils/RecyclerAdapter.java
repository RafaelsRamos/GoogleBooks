package com.example.fyldproject.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fyldproject.R;
import com.example.fyldproject.data.model.Book;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Book> bookList;
    private Context context;

    public RecyclerAdapter(List<Book> booksList, Context context ) {
        this.bookList = booksList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.albumTitle.setText("Test");

//        Glide.with(context).load("http://books.google.com/books/content?id=iJrS9blx6fIC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api").into(imageView);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView album;
        TextView albumTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addBooks(List<Book> books) {
        bookList.addAll(books);
    }
}
