package com.example.tp6;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_USER = 0;
    private static final int VIEW_TYPE_LOAD_MORE = 1;

    private final List<User> userList;

    private boolean showLoadMore = true;

    private final OnLoadMoreClickListener loadMoreClickListener;

    public interface OnLoadMoreClickListener {
        void onLoadMore();
    }

    public UserAdapter(List<User> userList, OnLoadMoreClickListener listener) {
        this.userList = userList;
        this.loadMoreClickListener = listener;
    }

    public void setShowLoadMore(boolean show) {
        this.showLoadMore = show;
        notifyDataSetChanged();
    }

    // Menentukan tipe tampilan berdasarkan posisi
    @Override
    public int getItemViewType(int position) {
        if (position < userList.size()) {
            return VIEW_TYPE_USER;
        } else {
            return VIEW_TYPE_LOAD_MORE;
        }
    }

    // Menghitung jumlah total item dalam adapter
    @Override
    public int getItemCount() {
        return showLoadMore ? userList.size() + 1 : userList.size();
    }

    // Membuat ViewHolder sesuai dengan tipe tampilan
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_USER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            return new LoadMoreViewHolder(view);
        }
    }

    // Mengikat data ke ViewHolder yang sesuai
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            User user = userList.get(position);
            ((UserViewHolder) holder).bind(user);
        } else if (holder instanceof LoadMoreViewHolder) {
            ((LoadMoreViewHolder) holder).btnLoadMore.setOnClickListener(v -> {
                if (loadMoreClickListener != null) {
                    loadMoreClickListener.onLoadMore();
                }
            });
        }
    }

    // ViewHolder untuk item user
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivImage;
        private final TextView tvName, tvSpecies;
        private final LinearLayout theCard;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvSpecies = itemView.findViewById(R.id.tvSpecies);
            theCard = itemView.findViewById(R.id.theCard);
        }

        // Mengikat data user ke tampilan
        public void bind(User user) {
            tvName.setText(user.getName() != null ? user.getName() : "Unknown");
            tvSpecies.setText(user.getSpecies() != null ? user.getSpecies() : "Unknown");

            // Menampilkan gambar menggunakan Picasso
            if (user.getImage() != null && !user.getImage().isEmpty()) {
                Picasso.get().load(user.getImage())
                        .placeholder(R.drawable.ic_launcher_background) // Gambar sementara
                        .error(R.drawable.ic_launcher_background)       // Gambar jika gagal
                        .into(ivImage);
            } else {
                ivImage.setImageResource(R.drawable.ic_launcher_background);
            }

            // Mengatur aksi klik pada kartu untuk membuka DetailActivity
            theCard.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra("user_id", user.getId());
                itemView.getContext().startActivity(intent);
            });
        }
    }

    // ViewHolder untuk item "Load More"
    public static class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        Button btnLoadMore;

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            btnLoadMore = itemView.findViewById(R.id.btnLoadMore);
        }
    }
}
