package com.example.fyldproject.ui.home;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.fyldproject.data.api.Client;
import com.example.fyldproject.data.api.Service;
import com.example.fyldproject.data.model.Book;
import com.example.fyldproject.data.model.BooksResponse;
import com.example.fyldproject.utils.RecyclerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    String searchQuery = null;

    private Service service;

    private int startIndex = 1;

    final String API_KEY = "AIzaSyDWk6o5CEl2-GQ8ypWF4XTRmMQSC97QiY4";

    // Pagination Variables
    private boolean isLoading = true;
    private int visibleItemCount, totalItemCount, pastVisibleItems, previousTotal, view_threshold = 20;

    private int totalBooksInQuery;

    private HomeFragment.BookSelectedListener bookSelectedListener;

    public void sendRequest (View view) {
        service = Client.getClient().create(Service.class);

        int numberOfBooks = 20;
        Call<BooksResponse> call = service.getBooksFromQuery(API_KEY, "book", numberOfBooks, startIndex);
        call.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {

                List<Book> books = response.body().getBooks();
                totalBooksInQuery = response.body().getTotalItems();
                for (Book bk : books) {
                    System.out.println(bk.getId());
                }
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {

            }
        });
    }
}
