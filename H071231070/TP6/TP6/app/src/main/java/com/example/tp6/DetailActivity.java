package com.example.tp6;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivImage;
    private TextView tvName, tvStatus, tvSpecies, tvGender, tvError;
    private ProgressBar progressBar;
    private Button btnRefresh;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mengaktifkan tampilan edge-to-edge (tanpa batas)
        EdgeToEdge.enable(this);

        // Mengatur layout activity_detail.xml sebagai tampilan utama activity ini
        setContentView(R.layout.activity_detail);

        // Inisialisasi semua elemen UI berdasarkan ID-nya
        ivImage = findViewById(R.id.ivImage);
        tvName = findViewById(R.id.tvName);
        tvStatus = findViewById(R.id.tvStatus);
        tvSpecies = findViewById(R.id.tvSpecies);
        tvGender = findViewById(R.id.tvGender);
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.tvError);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnBack = findViewById(R.id.ib_back);

        // Tombol kembali ke halaman sebelumnya
        btnBack.setOnClickListener(v -> onBackPressed());

        // Sembunyikan semua tampilan konten & tampilkan loading di awal
        progressBar.setVisibility(View.VISIBLE);
        ivImage.setVisibility(View.GONE);
        tvName.setVisibility(View.GONE);
        tvStatus.setVisibility(View.GONE);
        tvSpecies.setVisibility(View.GONE);
        tvGender.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.GONE);
        findViewById(R.id.tv_genderTag).setVisibility(View.GONE);
        findViewById(R.id.tvSpeciesTag).setVisibility(View.GONE);
        findViewById(R.id.tvStatusTag).setVisibility(View.GONE);
        findViewById(R.id.ll_properties).setVisibility(View.GONE);

        // Mengambil ID user yang dikirim dari Intent
        int userId = getIntent().getIntExtra("user_id", -1);

        // Jika ID valid, maka ambil data detail user dari API
        if (userId != -1) {
            loadUserData(userId);
        }

        // Jika tombol refresh ditekan, coba ulangi permintaan data
        btnRefresh.setOnClickListener(v -> {
            tvError.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            loadUserData(userId);
        });
    }

    // Fungsi untuk mengambil data detail user dari API
    private void loadUserData(int userId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<User> call = apiService.getUserDetail(userId);

        // Kirim permintaan asinkron
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // Jika berhasil dan data tersedia
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();

                    // Tampilkan data user ke tampilan
                    tvName.setText(user.getName());
                    tvStatus.setText(user.getStatus());
                    tvSpecies.setText(user.getSpecies());
                    tvGender.setText(user.getGender());

                    // Tampilkan gambar menggunakan Picasso
                    Picasso.get()
                            .load(user.getImage())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(ivImage);

                    // Tunda sedikit agar loading lebih smooth (2 detik)
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        progressBar.setVisibility(View.GONE);
                        ivImage.setVisibility(View.VISIBLE);
                        tvName.setVisibility(View.VISIBLE);
                        tvStatus.setVisibility(View.VISIBLE);
                        tvSpecies.setVisibility(View.VISIBLE);
                        tvGender.setVisibility(View.VISIBLE);
                        btnRefresh.setVisibility(View.GONE);
                    }, 2000);

                } else {
                    // Jika response gagal
                    showError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Jika permintaan gagal (tidak ada internet atau error server)
                progressBar.setVisibility(View.GONE);
                findViewById(R.id.tv_genderTag).setVisibility(View.GONE);
                findViewById(R.id.tvSpeciesTag).setVisibility(View.GONE);
                findViewById(R.id.tvStatusTag).setVisibility(View.GONE);
                findViewById(R.id.ll_properties).setVisibility(View.GONE);
                showError();
            }
        });
    }

    // Fungsi untuk menampilkan pesan error dan tombol refresh
    private void showError() {
        tvError.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.VISIBLE);
    }
}
