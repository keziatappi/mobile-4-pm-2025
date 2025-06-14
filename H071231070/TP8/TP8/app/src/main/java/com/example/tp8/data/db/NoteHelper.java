package com.example.tp8.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteHelper {
    private static final String DATABASE_TABLE = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static SQLiteDatabase database;
    private static NoteHelper INSTANCE;

    private NoteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static NoteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NoteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

//    open dan close database
    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

//untuk ambil smua data di tabel
    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.NoteColumns._ID + " DESC"
        );
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                DatabaseContract.NoteColumns._ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, DatabaseContract.NoteColumns._ID
                + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, DatabaseContract.NoteColumns._ID + " = ?",
                new String[]{id});
    }

    public Cursor searchByTitle(String keyword) {
        return database.query(
                DATABASE_TABLE,
                null,
                DatabaseContract.NoteColumns.TITLE + " LIKE ?",
                new String[]{"%" + keyword + "%"},
                null,
                null,
                DatabaseContract.NoteColumns._ID + " DESC"
        );
    }
}
