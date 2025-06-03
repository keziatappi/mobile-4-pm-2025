package com.example.tp5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FavoriteFragment extends Fragment {
    private ProgressBar progressBar;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.rv_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi ProgressBar
        progressBar = view.findViewById(R.id.progressBar);

        // Sembunyikan RecyclerView, tampilkan ProgressBar saat data sedang dimuat
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        // Menjalankan proses loading data di background thread
        executor.execute(() -> {
            try {
                Thread.sleep(500); // Simulasi delay pemrosesan data
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Setelah proses selesai, kembali ke UI thread untuk update tampilan
            handler.post(() -> {
                // Ambil daftar buku favorit dari data source
                List<Book> favoriteBooks = BookDataSource.getFavoriteBooks();

                // Buat adapter baru dengan data buku favorit
                adapter = new BookAdapter(
                        getContext(),
                        favoriteBooks,
                        R.layout.item_favorite_book);

                recyclerView.setAdapter(adapter);

                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            });
        });

        // Mengembalikan tampilan fragment ke sistem
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Memuat ulang data favorit setiap kali fragment ini muncul kembali
        loadFavorites(); // Memuat ulang data favorit dari data source
    }

    // Fungsi untuk memuat ulang daftar buku favorit ke adapter
    private void loadFavorites() {
        // Ambil data terbaru dari data source
        List<Book> favoriteBooks = BookDataSource.getFavoriteBooks();

        // Buat dan set ulang adapter ke RecyclerView
        adapter = new BookAdapter(
                getContext(),
                favoriteBooks,
                R.layout.item_favorite_book);
        recyclerView.setAdapter(adapter);
    }

}
