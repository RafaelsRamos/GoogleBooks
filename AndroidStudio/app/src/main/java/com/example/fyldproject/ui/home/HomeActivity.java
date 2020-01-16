package com.example.fyldproject.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.fyldproject.R;
import com.example.fyldproject.data.model.Book;

public class HomeActivity extends AppCompatActivity implements HomeFragment.BookSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    public void init() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment fragmentSettings = new HomeFragment();
        fragmentTransaction.add(R.id.home_fragment, fragmentSettings);
        fragmentTransaction.commit();
    }

    private Book bookSelected;
    @Override
    public void OnBookSelected(Book book) {
        bookSelected = book;
    }

    public Book getBookSelected() {
        return bookSelected;
    }
}
