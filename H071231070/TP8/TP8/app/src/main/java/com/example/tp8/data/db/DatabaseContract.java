package com.example.tp8.data.db;

import android.provider.BaseColumns;

//disini untuk definisi struktur tabel dan kolom
public class DatabaseContract {
    public static String TABLE_NAME="Note";

    public static final class NoteColumns implements BaseColumns {
        public static String TITLE="title";
        public static String CONTENT="content";
        public static String CREATED_DATE="created_date";
    }
}