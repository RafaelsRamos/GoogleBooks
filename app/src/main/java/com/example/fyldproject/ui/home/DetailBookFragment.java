package com.example.fyldproject.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.example.fyldproject.R;
import com.example.fyldproject.data.model.Book;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailBookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DetailBookFragment extends Fragment implements View.OnClickListener {
    private Book bookSelected;
    private Button buyButton;
    private ImageView favoriteImageView;

    private List<Book> favoriteBooks = new ArrayList<>();
    private Boolean isBookInFavorites = false;
    private static final String FILE_NAME = "favorites.json";
    public DetailBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity activityInstance = (HomeActivity) getActivity();
        bookSelected = activityInstance.getBookSelected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_book, container, false);
        buyButton = v.findViewById(R.id.detail_view_buy_button);
        buyButton.setOnClickListener(this);

        favoriteImageView = v.findViewById(R.id.favorite_image_view);
        favoriteImageView.setOnClickListener(this);

        checkBookFavorites();

        setUIElements(v);
        return v;
    }

    private void checkBookFavorites() {
        retrieveFavorites();
        for (Book book : favoriteBooks) {
            if (book.getId().equals(bookSelected.getId())) {
                System.out.println("YOYOYOY WE HAVE ONE OVER HERE");
                isBookInFavorites = true;
            }

        }

    }

    private void setUIElements(View v) {
        // Set image
        ImageView thumbnail = v.findViewById(R.id.detail_view_thumbnail);
        GlideUrl glideUrl = new GlideUrl(bookSelected.getImageThumbnail());
        Glide.with(getActivity().getApplicationContext()).load(glideUrl).into(thumbnail);

        // set title
        TextView title = v.findViewById(R.id.detail_view_title);
        title.setText(bookSelected.getTitle());

        // set authors
        TextView authors = v.findViewById(R.id.detail_view_authors);
        authors.setText(TextUtils.join(", ", bookSelected.getAuthors()));

        // set description
        TextView description = v.findViewById(R.id.detail_view_description);
        description.setText(bookSelected.getDescription());

        // set favorite imageview image
        setFavoriteImage();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_view_buy_button:
                openBuyLink();
                break;
            case R.id.favorite_image_view:
                if (!isBookInFavorites) {
                    sendBookToFavorites();
                    isBookInFavorites = true;
                } else {
                    isBookInFavorites = false;
                    retrieveFavorites();
                }
                setFavoriteImage();
                break;
        }
    }

    private void setFavoriteImage() {
        if (isBookInFavorites)
            favoriteImageView.setImageResource(R.mipmap.favorite);
        else
            favoriteImageView.setImageResource(R.mipmap.not_favorite);
    }

    private void sendBookToFavorites() {
        favoriteBooks.add(bookSelected);
        Gson gson = new Gson();

        FileOutputStream fileOutputStream = null;
        String jsonString = gson.toJson(favoriteBooks);
        try {
            fileOutputStream = getActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void retrieveFavorites() {
        Gson gson = new Gson();

        FileInputStream fis = null;

        String jsonText = null;
        try {
            fis = getActivity().openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while((jsonText = bufferedReader.readLine()) != null) {
                stringBuilder.append(jsonText).append("\n");
            }

            jsonText = stringBuilder.toString();
            favoriteBooks = Arrays.asList(gson.fromJson(jsonText, Book[].class));

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

    }

    private void openBuyLink() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookSelected.getBuyUrl()));
        startActivity(browserIntent);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
