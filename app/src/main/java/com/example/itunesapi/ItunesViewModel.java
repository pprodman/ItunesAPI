package com.example.itunesapi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.itunesapi.models.Itunes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItunesViewModel extends AndroidViewModel {


    MutableLiveData<Itunes.Respuesta> respuestaMutableLiveData = new MutableLiveData<>();
    static MutableLiveData<Itunes.Contenido> elementoSeleccionado = new MutableLiveData<>();

    public ItunesViewModel(@NonNull Application application) {
        super(application);
    }

    // Buscar elemento en la API
    public void buscar(String term){
        Itunes.api.buscar(term).enqueue(new Callback<Itunes.Respuesta>() {
            @Override
            public void onResponse(@NonNull Call<Itunes.Respuesta> call, @NonNull Response<Itunes.Respuesta> response) {
                respuestaMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Itunes.Respuesta> call, @NonNull Throwable t) {}
        });
    }

    // Seleccionar elemento de la lista
    static void seleccionar(Itunes.Contenido contenido){
        elementoSeleccionado.setValue(contenido);
    }

    MutableLiveData<Itunes.Contenido> seleccionado(){
        return elementoSeleccionado;
    }



}