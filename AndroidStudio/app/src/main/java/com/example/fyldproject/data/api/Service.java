package com.example.fyldproject.data.api;

import com.example.fyldproject.data.model.BooksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("volumes")
//    Call<BooksResponse> getBooksFromQuery(@Query("key") String apiKey, @Query("q") String searchQuery);
    Call<BooksResponse> getBooksFromQuery(@Query("key") String apiKey, @Query("q") String searchQuery, @Query("maxResults") int maxResults, @Query("startIndex") int startIndex);

}



// Possible queries https://developers.google.com/books/docs/v1/using#APIKey
//&maxResults
//&startIndex=1