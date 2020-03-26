package com.example.threads;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokeManager {
    private Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.icndb.com/")
            .addConverterFactory(GsonConverterFactory.create()).build();
    private JokeService jokeService = retrofit.create(JokeService.class);

    public JokeService getJokeService() {
        return jokeService;
    }
}
