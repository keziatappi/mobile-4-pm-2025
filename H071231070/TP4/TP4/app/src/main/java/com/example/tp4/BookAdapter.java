package com.example.tp4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Book> bookList;
    private List<Book> fullBookList;
    private int layoutId;

    public BookAdapter(Context context, List<Book> bookList, int layoutId) {
        this.context = context;
        this.bookList = new ArrayList<>(bookList);
        this.fullBookList = new ArrayList<>(bookList);
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.ivCover.setImageURI(Uri.parse(book.getCover()));

        SpannableString spannableAuthor = new SpannableString(book.getAuthor() + " (" + book.getYear() + ")");

        spannableAuthor.setSpan(new StyleSpan(Typeface.ITALIC), 0, book.getAuthor().length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.tvAuthor.setText(spannableAuthor);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("book", book);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvAuthor;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }

    public void filter(String keyword) {
        bookList.clear();
        if (keyword == null || keyword.trim().isEmpty()) {
            bookList.addAll(fullBookList);
        } else {
            String lowerKeyword = keyword.toLowerCase();
            for (Book book : fullBookList) {
                if (book.getTitle().toLowerCase().contains(lowerKeyword)
                        || book.getAuthor().toLowerCase().contains(lowerKeyword)) {
                    bookList.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }
}