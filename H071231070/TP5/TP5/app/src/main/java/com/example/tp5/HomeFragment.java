package com.example.tp5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Meng-inflate layout untuk fragment ini
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi komponen UI
        recyclerView = view.findViewById(R.id.rv_book);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        SearchView searchView = view.findViewById(R.id.search_view);

        // Mengisi data dummy buku ke dalam data source
        BookDataSource.generateDummyBooks(requireContext());

        // Menetapkan layout grid untuk RecyclerView dengan jumlah kolom 3
        int spanCount = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), spanCount));

        // Menambahkan dekorasi jarak antar item dalam grid
        int spacing = 16;
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        // Inisialisasi adapter dan menghubungkannya ke RecyclerView
        adapter = new BookAdapter(
                requireContext(),
                (List<Book>) BookDataSource.getBooks(), // Data buku
                R.layout.item_book); // Layout untuk item buku
        recyclerView.setAdapter(adapter);

        // Menyiapkan executor dan handler untuk proses asynchronous (background thread)
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        // Menambahkan listener pada SearchView untuk menangani pencarian
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Saat user menekan tombol cari, filter data berdasarkan query
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Saat user mengetik teks, tampilkan progress bar
                progressBar.setVisibility(View.VISIBLE);

                // Menjalankan pencarian secara asynchronous untuk memberi efek loading
                executor.execute(() -> {
                    try {
                        Thread.sleep(500); // Simulasi delay pencarian
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Setelah selesai, jalankan di UI thread
                    handler.post(() -> {
                        adapter.filter(newText); // Filter hasil berdasarkan teks pencarian
                        progressBar.setVisibility(View.GONE); // Sembunyikan progress bar
                    });
                });

                return true;
            }
        });

        return view; // Kembalikan tampilan fragment
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); // Perbarui tampilan saat fragment aktif kembali
    }
}
