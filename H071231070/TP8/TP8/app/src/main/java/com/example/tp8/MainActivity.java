package com.example.tp8;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp8.data.db.NoteHelper;
import com.example.tp8.data.mapping.MappingHelper;
import com.example.tp8.data.model.Note;
import com.example.tp8.databinding.ActivityMainBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private RecyclerView rvNotes;
    private NoteAdapter noteAdapter;

    private NoteHelper notesHelper;

    private final int REQUEST_ADD = 100;
    private final int REQUEST_UPDATE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Mengaktifkan EdgeToEdge layout agar konten bisa di belakang status bar

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Menyesuaikan padding berdasarkan sistem bar (status bar dan navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Mengubah warna ikon pencarian menjadi putih
        ImageView searchIcon = binding.svNotes.findViewById(
                binding.svNotes.getContext().getResources()
                        .getIdentifier("android:id/search_mag_icon", null, null)
        );
        if (searchIcon != null) {
            searchIcon.setColorFilter(ContextCompat.getColor(this, R.color.textWhite), PorterDuff.Mode.SRC_IN);
        }

        // Inisialisasi RecyclerView dan adapter
        rvNotes = binding.rvNote;
        noteAdapter = new NoteAdapter(this);
        rvNotes.setAdapter(noteAdapter);

        // Inisialisasi database helper
        notesHelper = NoteHelper.getInstance(getApplicationContext());

        // Listener untuk SearchView saat teks berubah
        binding.svNotes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Tidak melakukan apa-apa saat submit
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    loadNotes(); // Jika kosong, tampilkan semua catatan
                    return true;
                } else {
                    searchNotes(newText); // Jika ada teks, lakukan pencarian
                    return true;
                }
            }
        });

        // Load data catatan saat pertama kali masuk ke activity
        loadNotes();
    }

    // Fungsi untuk menampilkan semua catatan
    private void loadNotes() {
        new LoadNotesAsync(this, notes -> {
            ArrayList<Note> modifiedNotes = new ArrayList<>();

            // Menambahkan item dummy di posisi pertama untuk tombol "Add Note"
            Note dummyAddButton = new Note();
            dummyAddButton.setId(-1); // ID -1 digunakan sebagai penanda item dummy
            modifiedNotes.add(dummyAddButton);

            // Jika ada catatan, tambahkan ke list dan sembunyikan teks "No Notes"
            if (notes != null && notes.size() > 0) {
                binding.tvNoNote.setVisibility(View.GONE);
                modifiedNotes.addAll(notes);
            } else {
                binding.tvNoNote.setVisibility(View.VISIBLE); // Tampilkan jika kosong
            }

            // Tampilkan di adapter
            noteAdapter.setNotes(modifiedNotes);
        }).execute();
    }

    // Fungsi untuk mencari catatan berdasarkan judul
    private void searchNotes(String keyword) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            notesHelper.open();
            Cursor cursor = notesHelper.searchByTitle(keyword); // Query ke DB
            ArrayList<Note> filteredNotes = MappingHelper.mapCursorToArrayList(cursor);
            notesHelper.close();

            handler.post(() -> {
                ArrayList<Note> modifiedNotes = new ArrayList<>();

                // Tambahkan item dummy di awal
                Note dummyAddButton = new Note();
                dummyAddButton.setId(-1);
                modifiedNotes.add(dummyAddButton);

                // Tambahkan hasil pencarian ke list
                if (filteredNotes.size() > 0) {
                    modifiedNotes.addAll(filteredNotes);
                }

                noteAdapter.setNotes(modifiedNotes);
            });
        });
    }

    // Menangani hasil dari aktivitas lain (add/update/delete note)
    @Override
    protected void onActivityResult(int requestcode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        if (requestcode == REQUEST_ADD) {
            if (resultCode == NoteFormActivity.RESULT_ADD) {
                loadNotes(); // Refresh data setelah menambah
            }
        } else if (requestcode == REQUEST_UPDATE) {
            if (resultCode == NoteFormActivity.RESULT_UPDATE) {
                loadNotes(); // Refresh data setelah update
            } else if (resultCode == NoteFormActivity.RESULT_DELETE) {
                loadNotes(); // Refresh data setelah hapus
            }
        }
    }

    // Menutup helper saat activity dihancurkan
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notesHelper != null) {
            notesHelper.close();
        }
    }

    // Kelas inner untuk load data secara asynchronous menggunakan WeakReference
    private static class LoadNotesAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallBack> weakCallback;

        // Konstruktor menerima context dan callback
        private LoadNotesAsync(Context context, LoadNotesCallBack callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        // Fungsi utama untuk menjalankan proses load secara background
        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                Context context = weakContext.get();

                if (context != null) {
                    NoteHelper noteHelper = NoteHelper.getInstance(context);
                    noteHelper.open();

                    // Ambil semua data catatan dari DB
                    Cursor notesCursor = noteHelper.queryAll();
                    ArrayList<Note> notes = MappingHelper.mapCursorToArrayList(notesCursor);

                    noteHelper.close();

                    handler.post(() -> {
                        LoadNotesCallBack callBack = weakCallback.get();
                        if (callBack != null) {
                            callBack.postExecute(notes); // Kirim hasilnya ke callback
                        }

                    });
                }
            });
        }
    }

    // Interface callback yang digunakan oleh LoadNotesAsync
    interface LoadNotesCallBack {
        void postExecute(ArrayList<Note> notes);
    }
}
