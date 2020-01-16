package com.example.fyldproject.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.fyldproject.R;
import com.example.fyldproject.data.api.Client;
import com.example.fyldproject.data.api.Service;
import com.example.fyldproject.data.model.Book;
import com.example.fyldproject.data.model.BooksResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private GridLayoutManager layoutManager;

    private Service service;

    private int page_number = 1;
    private int item_count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialization
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        service = Client.getClient().create(Service.class);

        Call<BooksResponse> call = service.getBooksFromQuery("book", item_count, page_number);
        call.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                System.out.println(response);
//                List<Book> books = response.body().getItems();
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {

            }
        });

    }
}
