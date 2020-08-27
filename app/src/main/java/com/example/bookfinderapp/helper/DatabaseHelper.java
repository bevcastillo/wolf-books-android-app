package com.example.bookfinderapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bookfinderapp.models.VolumeBooks;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "bookmarksdb";
    private static final String BOOKMARKTBL = "bookmarks_tbl";
    private static final String COL_ID = "ID";
    private static final String COL_TITLE = "title";
    private static final String COL_AUTHOR = "author";
    private static final String COL_THUMBNAIL = "thumbnail_link";
    private static final String COL_PUBLISHER = "publisher";
    private static final String COL_PUBLISHED_DATE = "published_date";
    private static final String COL_DESC = "description";
    private static final String COL_RATING = "rating";
    private static final String COL_RATING_COUNT = "rating_count";
    private static final String COL_CATEGORY = "category";
    private static final String COL_PRICE = "price";
    private static final String COL_CURRENCY = "currency";
    private static final String COL_LANG = "language";
    private static final String COL_PAGE_COUNT = "page_count";
    private static final String COL_BUY_LINK = "buy_link";
    private static final String COL_PREV_LINK = "prev_link";
    private static final String COL_IS_BOOKMARK = "is_bookmark";

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
            + BOOKMARKTBL
            + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
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

    public long addBookmark( String title, String authors, String description, String publisher,
                             String publishedDate, String categories, String thumbnail, String previewLink,
                             String price, String currencyCode, String buyLink, String language,
                             int pageCount, double averageRating, int ratingsCount, boolean isBookmark) {

        SQLiteDatabase db = this.getWritableDatabase();

        //creating content values
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_AUTHOR, authors);
        values.put(COL_DESC, description);
        values.put(COL_PUBLISHER, publisher);
        values.put(COL_PUBLISHED_DATE, publishedDate);
        values.put(COL_CATEGORY, categories);
        values.put(COL_THUMBNAIL, thumbnail);
        values.put(COL_PREV_LINK, previewLink);
        values.put(COL_PRICE, price);
        values.put(COL_CURRENCY, currencyCode);
        values.put(COL_BUY_LINK, buyLink);
        values.put(COL_LANG, language);
        values.put(COL_PAGE_COUNT, pageCount);
        values.put(COL_RATING, averageRating);
        values.put(COL_RATING_COUNT, ratingsCount);
        values.put(COL_IS_BOOKMARK, isBookmark);

        long insert = db.insert(BOOKMARKTBL,null,values);
        return insert;

    }

    //displaying all data
    public ArrayList<VolumeBooks> getAll() {
        ArrayList<VolumeBooks> volumeBooksArrayList = new ArrayList<>();

        String selecctQuery = "SELECT * FROM " + BOOKMARKTBL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selecctQuery,null);

        //
        if (cursor.moveToFirst()) {
            do {
                VolumeBooks volumeBooks = new VolumeBooks();
                volumeBooks.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
//                String title, String authors, String description, String publisher,
//                        String publishedDate, String categories, String thumbnail, String previewLink,
//                        String price, String currencyCode, String buyLink, String language,
//                int pageCount, double averageRating, int ratingsCount, boolean isBookmark

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
            } while (cursor.moveToNext());
        }

        return volumeBooksArrayList;
    }


    public void removeBookmark(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BOOKMARKTBL,COL_ID+" = ? ", new String[]{String.valueOf(id)});
    }












}
