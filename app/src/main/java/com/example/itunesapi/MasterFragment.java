package com.example.itunesapi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.example.itunesapi.databinding.FragmentMasterBinding;
import com.example.itunesapi.databinding.ViewholderContenidoBinding;
import com.example.itunesapi.models.Itunes;

import java.util.List;


public class MasterFragment extends Fragment {
    private FragmentMasterBinding binding;
    private NavController navController;
    private ItunesViewModel itunesViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMasterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        itunesViewModel = new ViewModelProvider(requireActivity()).get(ItunesViewModel.class);

        ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
        binding.contenidos.setAdapter(contenidosAdapter);

        binding.texto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                itunesViewModel.buscar(s);
                return false;
            }
        });

        itunesViewModel.respuestaMutableLiveData.observe(getViewLifecycleOwner(), respuesta -> {
            if (respuesta != null && respuesta.results != null) {
                contenidosAdapter.establecerListaContenido(respuesta.results);
            }
        });
    }

    class ContenidosAdapter extends RecyclerView.Adapter<ContenidoViewHolder>{

        List<Itunes.Contenido> contenidoList;

        @NonNull
        @Override
        public ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContenidoViewHolder(ViewholderContenidoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContenidoViewHolder holder, int position) {
            Itunes.Contenido contenido = contenidoList.get(position);

            // Establecer datos en el ViewHolder
            holder.binding.title.setText(contenido.trackName);
            holder.binding.artist.setText(contenido.artistName);
            Glide.with(requireActivity()).load(contenido.artworkUrl100).into(holder.binding.artwork);

            holder.itemView.setOnClickListener(v -> {
                itunesViewModel.seleccionar(contenido);
                navController.navigate(R.id.action_MasterFragment_to_detailsFragment);
            });
        }

        @Override
        public int getItemCount() {
            return contenidoList == null ? 0 : contenidoList.size();
        }

        void establecerListaContenido(List<Itunes.Contenido> contenidoList){
            this.contenidoList = contenidoList;
            notifyDataSetChanged();
        }
    }

    static class ContenidoViewHolder extends RecyclerView.ViewHolder {
        ViewholderContenidoBinding binding;

        public ContenidoViewHolder(@NonNull ViewholderContenidoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

