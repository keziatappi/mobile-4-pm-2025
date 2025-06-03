package com.example.tp8;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp8.data.model.Note;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

// Adapter untuk RecyclerView yang menampilkan daftar Note
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final ArrayList<Note> notes = new ArrayList<>();
    private final Activity activity;

    // Konstruktor menerima activity sebagai parameter
    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    // Digunakan untuk menetapkan daftar note baru ke adapter
    public void setNotes(ArrayList<Note> notes) {
        this.notes.clear();
        if (notes.size() > 0) {
            this.notes.addAll(notes);
        }
        notifyDataSetChanged(); // ksh tau RecyclerView klo datanya berubah
    }

    // Membuat ViewHolder baru untuk item note
    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    // Mengisi data pada ViewHolder berdasarkan posisi
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        Note note = notes.get(position); // Ambil note berdasarkan posisi

        // Jika ID == -1, itu adalah tombol "Add Note" palsu
        if (note.getId() == -1) {
            holder.ll_item.setBackgroundResource(R.drawable.shape_grey);
            holder.iv_add.setVisibility(View.VISIBLE);
            holder.tv_title.setVisibility(View.GONE);
            holder.tv_content.setVisibility(View.GONE);
            holder.tv_createdDate.setVisibility(View.GONE);

            // Klik tombol tambah, buka form input note
            holder.iv_add.setOnClickListener(v -> {
                Intent intent = new Intent(activity, NoteFormActivity.class);
                activity.startActivityForResult(intent, 100);
            });
        } else {
            // Jika item normal (bukan dummy), tampilkan kontennya
            holder.iv_add.setVisibility(View.GONE);
            holder.tv_title.setVisibility(View.VISIBLE);
            holder.tv_content.setVisibility(View.VISIBLE);
            holder.tv_createdDate.setVisibility(View.VISIBLE);

            // Validasi bahwa data tidak null
            if (note.getTitle() != null || note.getContent() != null || note.getCreatedDate() != null) {
                String title = note.getTitle();
                String content = note.getContent();
                String createdDate = note.getCreatedDate();

                // Potong judul jika lebih dari 10 karakter
                if (title.length() > 10) {
                    title = title.substring(0, 11) + "...";
                    holder.tv_title.setText(title);
                } else {
                    holder.tv_title.setText(note.getTitle());
                }

                // Potong konten jika lebih dari 140 karakter
                if (content.length() > 140) {
                    content = content.substring(0, 141) + "...";
                    holder.tv_content.setText(content);
                } else {
                    holder.tv_content.setText(note.getContent());
                }

                // Set tanggal
                holder.tv_createdDate.setText(createdDate);

                // Klik pada item akan membuka detail menggunakan BottomSheetDialog
                holder.itemView.setOnClickListener(v -> {
                    showNoteDetailDialog(note);
                });
            } else {
                // Jika datanya kosong/null
                holder.tv_title.setText("");
                holder.tv_content.setText("");
                holder.tv_createdDate.setText("");
            }
        }
    }

    // Mengembalikan jumlah item di adapter
    @Override
    public int getItemCount() {
        return notes.size();
    }

    // ViewHolder untuk item note
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_content, tv_createdDate;
        private ImageView iv_add;
        private LinearLayout ll_item;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            // Hubungkan komponen layout ke variabel
            ll_item = itemView.findViewById(R.id.ll_item);
            iv_add = itemView.findViewById(R.id.iv_add);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_createdDate = itemView.findViewById(R.id.tv_createdDate);
        }
    }

    // Menampilkan dialog detail note (BottomSheet) saat item diklik
    private void showNoteDetailDialog(Note note) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.item_note_detail, null);
        bottomSheetDialog.setContentView(view);

        // Ambil referensi dari layout bottom sheet
        LinearLayout ll_itemDetail = view.findViewById(R.id.ll_itemDetail);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        TextView tv_createdDate = view.findViewById(R.id.tv_createdDate);
        Button btn_edit = view.findViewById(R.id.btn_edit);

        // Tampilkan data note
        tv_title.setText(note.getTitle());
        tv_content.setText(note.getContent());
        tv_createdDate.setText(note.getCreatedDate());

        // Tombol edit mengarahkan ke form edit note
        btn_edit.setOnClickListener(v -> {
            Intent intent = new Intent(activity, NoteFormActivity.class);
            intent.putExtra(NoteFormActivity.EXTRA_NOTE, note);
            activity.startActivityForResult(intent, NoteFormActivity.REQUEST_UPDATE);
            bottomSheetDialog.dismiss(); // Tutup dialog setelah klik
        });

        bottomSheetDialog.show(); // Tampilkan dialog
    }
}
