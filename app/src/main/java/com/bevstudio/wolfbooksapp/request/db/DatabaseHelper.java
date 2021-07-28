package com.bevstudio.wolfbooksapp.request.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bevstudio.wolfbooksapp.model.db.VolumeBooks;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "bookmarksdb";
    public static final String BOOKMARKTBL = "bookmarks_tbl";
    public static final String COL_ID = "ID";
    public static final String COL_VOL_ID = "volume_id";
    public static final String COL_IS_BOOKMARK = "is_bookmark";

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
            + BOOKMARKTBL
            + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_VOL_ID + " TEXT, "
            + COL_IS_BOOKMARK + " BOOLEAN );";


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS '" + BOOKMARKTBL + "' ");
        onCreate(db);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTS);

    }

    public void addBookmark(VolumeBooks volumeBooks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_ID, volumeBooks.getStr_id());
        values.put(COL_VOL_ID, volumeBooks.getVolumeId());
        values.put(COL_IS_BOOKMARK, volumeBooks.isBookmark());

        db.insert(BOOKMARKTBL,null,values);
        db.close();
    }

    //getting the VolumeId
    public ArrayList<VolumeBooks> getVolumeId(String volumeId) {
        ArrayList<VolumeBooks> volumeBooksArrayList = new ArrayList<VolumeBooks>();

        String selectQuery = " SELECT * FROM " + BOOKMARKTBL + " WHERE " + DatabaseHelper.COL_VOL_ID + " = " + volumeId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                VolumeBooks volumeBooks = new VolumeBooks();
                volumeBooks.setVolumeId(cursor.getString(cursor.getColumnIndex(COL_VOL_ID)));
                volumeBooksArrayList.add(volumeBooks);
            } while (cursor.moveToNext());
        }
        return volumeBooksArrayList;
    }

    //displaying all data
    public ArrayList<VolumeBooks> getAll() {
        ArrayList<VolumeBooks> volumeBooksArrayList = new ArrayList<VolumeBooks>();

        String selectQuery = "SELECT * FROM " + BOOKMARKTBL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //
        if (cursor.moveToFirst()) {
            do {
                VolumeBooks volumeBooks = new VolumeBooks();
                volumeBooks.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                volumeBooks.setVolumeId(cursor.getString(cursor.getColumnIndex(COL_VOL_ID)));
                volumeBooks.setBookmark(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(COL_IS_BOOKMARK))));
                volumeBooksArrayList.add(volumeBooks);

            } while (cursor.moveToNext());
        }

        return volumeBooksArrayList;
    }


    public void removeBookmark(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BOOKMARKTBL,COL_ID+" = ? ", new String[]{String.valueOf(id)});
    }


}
