package com.example.tp8.data.mapping;

import android.database.Cursor;


import com.example.tp8.data.db.DatabaseContract;
import com.example.tp8.data.model.Note;

import java.util.ArrayList;

//disini untuk ubah data yang diambil dari sqlite ke objek java
public class MappingHelper {
    public static ArrayList<Note> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.CONTENT));
            String createdDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.CREATED_DATE));
            notes.add(new Note(id, title, content, createdDate));
        }
        return notes;
    }
}