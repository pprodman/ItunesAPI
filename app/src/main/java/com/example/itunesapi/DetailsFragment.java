package com.example.itunesapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.itunesapi.databinding.FragmentDetailsBinding;
import com.example.itunesapi.models.Itunes;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private ItunesViewModel itunesViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itunesViewModel = new ViewModelProvider(requireActivity()).get(ItunesViewModel.class);

        itunesViewModel.seleccionado().observe(getViewLifecycleOwner(), this::mostrarDetalles);
    }

    // Mostrar detalles del elemento seleccionado
    private void mostrarDetalles(Itunes.Contenido contenido) {
        if (contenido != null) {
            binding.title.setText(contenido.trackName);
            binding.artist.setText(contenido.artistName);
            Glide.with(this).load(contenido.artworkUrl100).into(binding.artwork);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

