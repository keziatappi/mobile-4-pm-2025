package com.example.tp4;

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

        // Inisialisasi image picker launcher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
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
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        EditText etTitle = view.findViewById(R.id.et_title);
        EditText etAuthor = view.findViewById(R.id.et_author);
        EditText etYear = view.findViewById(R.id.et_year);
        EditText etBlurb = view.findViewById(R.id.et_blurb);
        ivCover = view.findViewById(R.id.iv_cover);
        ImageView ivCover = view.findViewById(R.id.iv_cover);
        Button btnSimpan = view.findViewById(R.id.btn_save);

        ivCover.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnSimpan.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String author = etAuthor.getText().toString();
            String year = etYear.getText().toString();
            String blurb = etBlurb.getText().toString();
            boolean like = false;
            String cover= selectedImageUri != null ? selectedImageUri.toString() : String.valueOf(R.drawable.default_book_cover);;

            Book newBook = new Book(title, author, year, blurb, false, cover);
            BookDataSource.addBook(newBook);

            Fragment homeFragment = new HomeFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .addToBackStack(null)
                    .commit();

            Toast.makeText(getContext(), "Data disimpan", Toast.LENGTH_SHORT).show();
        });
        return view;
    }

}