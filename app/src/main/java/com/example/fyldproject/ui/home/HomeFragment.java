package com.example.fyldproject.ui.home;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyldproject.R;
import com.example.fyldproject.data.api.Client;
import com.example.fyldproject.data.api.Service;
import com.example.fyldproject.data.model.Book;
import com.example.fyldproject.data.model.BooksResponse;
import com.example.fyldproject.utils.RecyclerAdapter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements RecyclerAdapter.OnBookClickListener, View.OnClickListener {

    private static final String FILE_NAME = "favorites.json";

    // UI
    private ImageView searchBtn;
    private EditText searchQueryText;
    private Boolean isFilteredByFavorites = false;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private Service service;
    private RecyclerAdapter adapter;

    private int startIndex = 1;

    final String API_KEY = "AIzaSyDWk6o5CEl2-GQ8ypWF4XTRmMQSC97QiY4";

    // Pagination Variables
    private boolean isLoading = true;
    private int visibleItemCount, totalItemCount, pastVisibleItems, previousTotal, view_threshold = 20;

    private int totalBooksInQuery;

    private BookSelectedListener bookSelectedListener;

    RecyclerAdapter.OnBookClickListener clickListener;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        // Initialization

        searchBtn = v.findViewById(R.id.search_button);
        searchBtn.setOnClickListener(this);

        ImageView favoriteFilterBtn = v.findViewById(R.id.favorite_filter);
        favoriteFilterBtn.setOnClickListener(this);

        searchQueryText = v.findViewById(R.id.search_bar);

        recyclerView = v.findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        service = Client.getClient().create(Service.class);

        clickListener = this;

        sendWebRequest("Android");

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (dy > 0 && !isFilteredByFavorites) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    else if (pastVisibleItems + visibleItemCount == totalItemCount) {
                        startIndex += view_threshold;
                        performPagination();
                        isLoading = true;
                    }
                }
            }
        });
        return v;
    }

    private void sendWebRequest(String queryString) {
        // If QueryString is empty, substitute it for "Android"
        queryString = queryString.equals("") ? "Android" : queryString;

        int numberOfBooks = 20;
        Call<BooksResponse> call = service.getBooksFromQuery(API_KEY, queryString, numberOfBooks, startIndex);
        call.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {

                List<Book> books = response.body().getBooks();
                totalBooksInQuery = response.body().getTotalItems();
                adapter = new RecyclerAdapter(books, getActivity().getApplicationContext(), clickListener);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {

            }
        });
    }

    private void performPagination() {
        Call<BooksResponse> call = service.getBooksFromQuery(API_KEY, "book", view_threshold, startIndex);
        call.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                totalBooksInQuery = response.body().getTotalItems();
                List<Book> books = response.body().getBooks();
                if (books != null)
                    adapter.addBooks(books);

                if (totalItemCount == totalBooksInQuery)
                    Toast.makeText(getActivity().getApplicationContext(), "All items loaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBookClick(int position) {
        Book bookSelected = adapter.getBooks().get(position);

        bookSelectedListener.OnBookSelected(bookSelected);

        DetailBookFragment detailBookFragment = new DetailBookFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_fragment, detailBookFragment, "detailed_book_fragment")
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                sendWebRequest(searchQueryText.getText().toString());
                break;
            case R.id.favorite_filter:
                System.out.println("IS FILTERING? : " + isFilteredByFavorites);
                if (!isFilteredByFavorites) {
                    List<Book> favoriteBooks = retrieveFavorites();
                    if (favoriteBooks != null) {
                        isFilteredByFavorites = true;
                        adapter = new RecyclerAdapter(retrieveFavorites(), getActivity().getApplicationContext(), clickListener);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "No favorite books yet", Toast.LENGTH_LONG).show();
                    }
                } else {
                    isFilteredByFavorites = false;
                    sendWebRequest(searchQueryText.getText().toString()); // Back to regular search
                }
                break;
        }
    }


    public interface BookSelectedListener {
        void OnBookSelected(Book book);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bookSelectedListener = (BookSelectedListener) getActivity();
        } catch (Exception e) {}
    }

    private List<Book> retrieveFavorites() {
        Gson gson = new Gson();

        FileInputStream fis = null;

        String jsonText = null;

        List<Book> favoriteBooks = null;
        try {
            fis = getActivity().openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while((jsonText = bufferedReader.readLine()) != null) {
                stringBuilder.append(jsonText).append("\n");
            }

            jsonText = stringBuilder.toString();
            favoriteBooks = new LinkedList<>(Arrays.asList(gson.fromJson(jsonText, Book[].class)));
//            favoriteBooks = Arrays.asList(gson.fromJson(jsonText, Book[].class));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return favoriteBooks;
    }


}
