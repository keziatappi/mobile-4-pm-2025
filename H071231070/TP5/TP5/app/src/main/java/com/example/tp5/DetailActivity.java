package com.example.tp5;

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
        tvBlurb = findViewById(R.id.tv_blurb);
        btnAddToFavorite = findViewById(R.id.btn_add_to_favorite);

        // Mendapatkan objek Book yang dikirim dari Activity sebelumnya melalui Intent
        book = getIntent().getParcelableExtra("book");

        // Jika data buku berhasil diterima
        if (book != null) {
            // Menampilkan data buku ke tampilan
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
//            tvYear.setText(book.getYear());
            tvBlurb.setText(book.getBlurb());

            // Menampilkan gambar cover jika ada, jika tidak tampilkan gambar default
            if (book.getCover() != null) {
                ivCover.setImageURI(Uri.parse(book.getCover()));
            } else {
                ivCover.setImageResource(R.drawable.default_book_cover);
            }

            updateFavoriteButtonState();
        }

        // Tombol kembali (back), menutup Activity saat ditekan
        View ib_back = findViewById(R.id.ib_back);
        ib_back.setOnClickListener(v -> finish());

        // Logika saat tombol favorit ditekan
        btnAddToFavorite.setOnClickListener(v -> {
            // Jika buku sudah di favorit, hapus dari favorit
            if (BookDataSource.isFavorite(book)) {
                BookDataSource.removeFavorite(book);
                Toast.makeText(this, "Buku dihapus  dari favorit", Toast.LENGTH_SHORT).show();
            } else {
                // Jika belum di favorit, tambahkan
                BookDataSource.addFavorite(book);
                Toast.makeText(this, "Buku ditambahkan ke  favorit", Toast.LENGTH_SHORT).show();
            }
            // Perbarui tampilan tombol favorit setelah perubahan
            updateFavoriteButtonState();
        });
    }

    // Fungsi untuk memperbarui tampilan tombol berdasarkan status favorit
    private void updateFavoriteButtonState() {
        if (BookDataSource.isFavorite(book)) {
            btnAddToFavorite.setText("Hapus dari favorit"); // Ubah teks
            btnAddToFavorite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red))); // Ubah warna tombol menjadi merah
//            btnAddToFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_solid, 0, 0, 0);
            // Ikon favorit penuh (jika ingin menggunakan drawable ikon)
        } else {
            btnAddToFavorite.setText("Tambahkan ke favorit"); // Teks default
            btnAddToFavorite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green))); // Warna hijau
//            btnAddToFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_outline, 0, 0, 0);
            // Ikon outline (jika ingin menggunakan drawable ikon)
        }
    }

    // Fungsi tambahan yang belum digunakan, tapi tersedia untuk menambahkan buku ke data source
    private void addBookToFavorite(Book book) {
        BookDataSource.addBook(book);

        Toast.makeText(this, "Buku ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
    }
}
