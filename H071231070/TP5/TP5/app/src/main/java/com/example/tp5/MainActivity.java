package com.example.tp5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Mengaktifkan fitur Edge-to-Edge untuk UI yang lebih modern
        setContentView(R.layout.activity_main); // Menentukan layout activity

        loadFragment(new HomeFragment()); // Menampilkan fragment Home saat pertama kali activity dibuka

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation); // Inisialisasi bottom navigation

        // Menentukan aksi saat item pada bottom navigation dipilih
        bottomNavigationView.setOnItemSelectedListener(item -> {
            bottomNavigationView.getMenu().findItem(R.id.nav_home).setIcon(R.drawable.ic_home_outline);
            bottomNavigationView.getMenu().findItem(R.id.nav_add_book).setIcon(R.drawable.ic_add_outline);
            bottomNavigationView.getMenu().findItem(R.id.nav_favorite).setIcon(R.drawable.ic_favorite_outline);

            int itemId = item.getItemId();

            Fragment selectedFragment = null;

            // Menentukan fragment dan ikon berdasarkan item yang dipilih
            if (itemId == R.id.nav_home) {
                item.setIcon(R.drawable.ic_home_solid);
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_add_book) {
                item.setIcon(R.drawable.ic_add_solid);
                selectedFragment = new AddBookFragment();
            } else if (itemId == R.id.nav_favorite) {
                item.setIcon(R.drawable.ic_favorite_solid);
                selectedFragment = new FavoriteFragment();
            }

            // Jika fragment dipilih, lakukan transaksi untuk menggantinya di container
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });

    }

    // Fungsi untuk memuat fragment ke dalam fragment_container
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit(); // Lakukan penggantian fragment
            return true;
        }
        return false;
    }

}
