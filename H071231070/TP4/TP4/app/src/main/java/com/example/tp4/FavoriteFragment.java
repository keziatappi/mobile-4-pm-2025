package com.example.tp4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.rv_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Book> favoriteBooks = BookDataSource.getFavoriteBooks();
        adapter = new BookAdapter(
                getContext(),
                favoriteBooks,
                R.layout.item_favorite_book);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        adapter.notifyDataSetChanged();
        loadFavorites();
    }

    private void loadFavorites() {
        List<Book> favoriteBooks = BookDataSource.getFavoriteBooks();
        adapter = new BookAdapter(
                getContext(),
                favoriteBooks,
                R.layout.item_favorite_book);
        recyclerView.setAdapter(adapter);
    }

}