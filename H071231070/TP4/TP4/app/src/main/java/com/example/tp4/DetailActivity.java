package com.example.tp4;

import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class DetailActivity extends AppCompatActivity {

    private ImageView ivCover;
    private TextView tvTitle, tvAuthor, tvYear, tvBlurb;
    private Button btnAddToFavorite;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ivCover = findViewById(R.id.iv_cover);
        tvTitle = findViewById(R.id.tv_title);
        tvAuthor = findViewById(R.id.tv_author);
//        tvYear = findViewById(R.id.tv_year);
        tvBlurb = findViewById(R.id.tv_blurb);
        btnAddToFavorite = findViewById(R.id.btn_add_to_favorite);


        book = getIntent().getParcelableExtra("book");

        if (book != null) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
//            tvYear.setText(book.getYear());
            tvBlurb.setText(book.getBlurb());

            if (book.getCover() != null) {
                ivCover.setImageURI(Uri.parse(book.getCover()));
            } else {
                ivCover.setImageResource(R.drawable.default_book_cover); // Default image
            }

            updateFavoriteButtonState();
        }

        View ib_back = findViewById(R.id.ib_back);
        ib_back.setOnClickListener(v -> finish());

//        btnAddToFavorite.setOnClickListener(v -> {
//            BookDataSource.addFavorite(book);
//            Toast.makeText(this, "Buku ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
//        });

        btnAddToFavorite.setOnClickListener(v -> {
            if (BookDataSource.isFavorite(book)) {
                BookDataSource.removeFavorite(book);
                Toast.makeText(this, "Buku dihapus  dari favorit", Toast.LENGTH_SHORT).show();
            } else {
                BookDataSource.addFavorite(book);
                Toast.makeText(this, "Buku ditambahkan ke  favorit", Toast.LENGTH_SHORT).show();
            }
            updateFavoriteButtonState();
        });
    }

    private void updateFavoriteButtonState() {
        if (BookDataSource.isFavorite(book)) {
            btnAddToFavorite.setText("Hapus dari favorit");
            btnAddToFavorite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
//            btnAddToFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_solid, 0, 0, 0);
        } else {
            btnAddToFavorite.setText("Tambahkan ke favorit");
            btnAddToFavorite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
//            btnAddToFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_outline, 0, 0, 0);
        }
    }

    private void addBookToFavorite(Book book) {
        BookDataSource.addBook(book);

        Toast.makeText(this, "Buku ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
    }
}