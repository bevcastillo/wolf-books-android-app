package com.example.bookfinderapp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "bookmarks_tbl";
    private static final String COL1 = "ID";
    private static final String COL2 = "title";
    private static final String COL3 = "author";
    private static final String COL4 = "thumbnail_link";
    private static final String COL5 = "publisher";
    private static final String COL6 = "published_date";
    private static final String COL7 = "desc";
    private static final String COL8 = "rating";
    private static final String COL9 = "rating_count";
    private static final String COL10 = "category";
    private static final String COL11 = "price";
    private static final String COL12 = "currency";
    private static final String COL13 = "language";
    private static final String COL14 = "page_count";
    private static final String COL15 = "buy_link";
    private static final String COL16 = "prev_link";
    private static final String COL17 = "is_bookmark";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + COL2 + "TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
}
