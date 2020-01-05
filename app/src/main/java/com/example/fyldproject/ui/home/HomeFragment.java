package com.example.fyldproject.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements RecyclerAdapter.OnBookClickListener{

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


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        // Initialization
        recyclerView = v.findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        service = Client.getClient().create(Service.class);

        final RecyclerAdapter.OnBookClickListener clickListener = this;

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

                adapter = new RecyclerAdapter(books, getActivity().getApplicationContext(), clickListener);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
//                System.out.println("DY " + dy + " | pastvisible " + pastVisibleItems + " | total " + totalItemCount + " | " + visibleItemCount);
                if (dy > 0) {
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
}
