package com.example.bookfinderapp.request.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertBookmark( String id, boolean isBookmark) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_VOL_ID, id);
        values.put(DatabaseHelper.COL_IS_BOOKMARK, isBookmark);

        sqLiteDatabase.insert(DatabaseHelper.BOOKMARKTBL,null,values);

    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper.COL_ID,
                                        DatabaseHelper.COL_VOL_ID,
                                        DatabaseHelper.COL_IS_BOOKMARK };

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.BOOKMARKTBL, columns,
                                    null,null,null,null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }
}
