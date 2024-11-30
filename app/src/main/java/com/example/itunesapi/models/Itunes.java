package com.example.itunesapi.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Itunes {
    public static Api api = new Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);

    public interface Api {
        @GET("search/")
        Call<Respuesta> buscar(@Query("term") String texto);
    }

    public static class Respuesta {
        public List<Contenido> results;
    }

    public static class Contenido {
        public String artistName;
        public String trackName;
        public String artworkUrl100;
    }
}