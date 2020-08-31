package com.example.bookfinderapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bookfinderapp.models.VolumeBooks;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "bookmarksdb";
    public static final String BOOKMARKTBL = "bookmarks_tbl";
    public static final String COL_ID = "ID";
    public static final String COL_VOL_ID = "volume_id";
    public static final String COL_TITLE = "title";
    public static final String COL_AUTHOR = "author";
    public static final String COL_THUMBNAIL = "thumbnail_link";
    public static final String COL_PUBLISHER = "publisher";
    public static final String COL_PUBLISHED_DATE = "published_date";
    public static final String COL_DESC = "description";
    public static final String COL_RATING = "rating";
    public static final String COL_RATING_COUNT = "rating_count";
    public static final String COL_CATEGORY = "category";
    public static final String COL_PRICE = "price";
    public static final String COL_CURRENCY = "currency";
    public static final String COL_LANG = "language";
    public static final String COL_PAGE_COUNT = "page_count";
    public static final String COL_BUY_LINK = "buy_link";
    public static final String COL_PREV_LINK = "prev_link";
    public static final String COL_IS_BOOKMARK = "is_bookmark";

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
            + BOOKMARKTBL
            + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_VOL_ID + " TEXT, "
            + COL_TITLE + " TEXT, "
            + COL_AUTHOR + " TEXT, "
            + COL_DESC + " TEXT, "
            + COL_PUBLISHER + " TEXT, "
            + COL_PUBLISHED_DATE + " TEXT, "
            + COL_CATEGORY + " TEXT, "
            + COL_THUMBNAIL + " TEXT, "
            + COL_PREV_LINK + " TEXT, "
            + COL_PRICE + " TEXT, "
            + COL_CURRENCY + " TEXT, "
            + COL_BUY_LINK + " TEXT, "
            + COL_LANG + " TEXT, "
            + COL_PAGE_COUNT + " INTEGER, "
            + COL_RATING + " DOUBLE, "
            + COL_RATING_COUNT + " INTEGER, "
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

        values.put(COL_TITLE, volumeBooks.getTitle());
        values.put(COL_VOL_ID, volumeBooks.getVolumeId());
        values.put(COL_AUTHOR, volumeBooks.getAuthors());
        values.put(COL_DESC, volumeBooks.getDescription());
        values.put(COL_PUBLISHER, volumeBooks.getPublisher());
        values.put(COL_PUBLISHED_DATE, volumeBooks.getPublishedDate());
        values.put(COL_CATEGORY, volumeBooks.getCategories());
        values.put(COL_THUMBNAIL, volumeBooks.getThumbnail());
        values.put(COL_PREV_LINK, volumeBooks.getPreviewLink());
        values.put(COL_PRICE, volumeBooks.getPrice());
        values.put(COL_CURRENCY, volumeBooks.getCurrencyCode());
        values.put(COL_BUY_LINK, volumeBooks.getBuyLink());
        values.put(COL_LANG, volumeBooks.getLanguage());
        values.put(COL_PAGE_COUNT, volumeBooks.getPageCount());
        values.put(COL_RATING, volumeBooks.getAverageRating());
        values.put(COL_RATING_COUNT, volumeBooks.getRatingsCount());
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
                volumeBooks.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                volumeBooks.setAuthors(cursor.getString(cursor.getColumnIndex(COL_AUTHOR)));
                volumeBooks.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESC)));
                volumeBooks.setPublisher(cursor.getString(cursor.getColumnIndex(COL_PUBLISHER)));
                volumeBooks.setPublishedDate(cursor.getString(cursor.getColumnIndex(COL_PUBLISHED_DATE)));
                volumeBooks.setCategories(cursor.getString(cursor.getColumnIndex(COL_CATEGORY)));
                volumeBooks.setThumbnail(cursor.getString(cursor.getColumnIndex(COL_THUMBNAIL)));
                volumeBooks.setPreviewLink(cursor.getString(cursor.getColumnIndex(COL_PREV_LINK)));
                volumeBooks.setPrice(cursor.getString(cursor.getColumnIndex(COL_PRICE)));
                volumeBooks.setCurrencyCode(cursor.getString(cursor.getColumnIndex(COL_CURRENCY)));
                volumeBooks.setBuyLink(cursor.getString(cursor.getColumnIndex(COL_BUY_LINK)));
                volumeBooks.setLanguage(cursor.getString(cursor.getColumnIndex(COL_LANG)));
                volumeBooks.setPageCount(cursor.getInt(cursor.getColumnIndex(COL_PAGE_COUNT)));
                volumeBooks.setAverageRating(cursor.getDouble(cursor.getColumnIndex(COL_RATING)));
                volumeBooks.setRatingsCount(cursor.getInt(cursor.getColumnIndex(COL_PAGE_COUNT)));
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
