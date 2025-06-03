package com.example.tp4;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Reset semua icon ke versi outline
            bottomNavigationView.getMenu().findItem(R.id.nav_home).setIcon(R.drawable.ic_home_outline);
            bottomNavigationView.getMenu().findItem(R.id.nav_add_book).setIcon(R.drawable.ic_add_outline);
            bottomNavigationView.getMenu().findItem(R.id.nav_favorite).setIcon(R.drawable.ic_favorite_outline);

            // Set icon aktif sesuai item yang dipilih
            int itemId = item.getItemId();

            Fragment selectedFragment = null;

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

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}