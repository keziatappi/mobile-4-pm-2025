package com.example.tp5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddBookFragment extends Fragment {
    private ImageView ivCover;
    private Uri selectedImageUri;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inisialisasi image picker launcher untuk membuka galeri dan mengambil gambar
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Mengecek jika pemilihan gambar berhasil
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        ivCover.setImageURI(selectedImageUri);
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceAtate) {
        // Menghubungkan layout fragment dengan kode Java
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        // Inisialisasi komponen dari layout
        EditText etTitle = view.findViewById(R.id.et_title);
        EditText etAuthor = view.findViewById(R.id.et_author);
        EditText etYear = view.findViewById(R.id.et_year);
        EditText etBlurb = view.findViewById(R.id.et_blurb);
        ivCover = view.findViewById(R.id.iv_cover); // Inisialisasi ImageView untuk cover
        Button btnSimpan = view.findViewById(R.id.btn_save); // Tombol simpan

        // Ketika cover diklik, buka galeri untuk memilih gambar
        ivCover.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent); // Meluncurkan galeri
        });

        // Ketika tombol simpan diklik
        btnSimpan.setOnClickListener(v -> {
            // Mengambil data dari inputan
            String title = etTitle.getText().toString();
            String author = etAuthor.getText().toString();
            String year = etYear.getText().toString();
            String blurb = etBlurb.getText().toString();

            // Jika tidak ada gambar yang dipilih, gunakan gambar default
            String cover = selectedImageUri != null ? selectedImageUri.toString() : String.valueOf(R.drawable.default_book_cover);

            // Membuat objek Book baru
            Book newBook = new Book(title, author, year, blurb, false, cover);

            // Menambahkan buku ke sumber data
            BookDataSource.addBook(newBook);

            // Navigasi kembali ke HomeFragment setelah menyimpan data
            Fragment homeFragment = new HomeFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .addToBackStack(null)
                    .commit();

            // Menampilkan notifikasi bahwa data disimpan
            Toast.makeText(getContext(), "Data disimpan", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

}
