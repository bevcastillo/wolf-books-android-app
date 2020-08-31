package com.example.bookfinderapp.helper;

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

    public void insertBookmark( String title, String authors, String description, String publisher,
                             String publishedDate, String categories, String thumbnail, String previewLink,
                             String price, String currencyCode, String buyLink, String language,
                             int pageCount, double averageRating, int ratingsCount, boolean isBookmark) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TITLE, title);
        values.put(DatabaseHelper.COL_AUTHOR, authors);
        values.put(DatabaseHelper.COL_DESC, description);
        values.put(DatabaseHelper.COL_PUBLISHER, publisher);
        values.put(DatabaseHelper.COL_PUBLISHED_DATE, publishedDate);
        values.put(DatabaseHelper.COL_CATEGORY, categories);
        values.put(DatabaseHelper.COL_THUMBNAIL, thumbnail);
        values.put(DatabaseHelper.COL_PREV_LINK, previewLink);
        values.put(DatabaseHelper.COL_PRICE, price);
        values.put(DatabaseHelper.COL_CURRENCY, currencyCode);
        values.put(DatabaseHelper.COL_BUY_LINK, buyLink);
        values.put(DatabaseHelper.COL_LANG, language);
        values.put(DatabaseHelper.COL_PAGE_COUNT, pageCount);
        values.put(DatabaseHelper.COL_RATING, averageRating);
        values.put(DatabaseHelper.COL_RATING_COUNT, ratingsCount);
        values.put(DatabaseHelper.COL_IS_BOOKMARK, isBookmark);

        sqLiteDatabase.insert(DatabaseHelper.BOOKMARKTBL,null,values);

    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper.COL_ID,
                                        DatabaseHelper.COL_TITLE,
                                        DatabaseHelper.COL_AUTHOR,
                                        DatabaseHelper.COL_DESC,
                                        DatabaseHelper.COL_PUBLISHER,
                                        DatabaseHelper.COL_PUBLISHED_DATE,
                                        DatabaseHelper.COL_CATEGORY,
                                        DatabaseHelper.COL_THUMBNAIL,
                                        DatabaseHelper.COL_PREV_LINK,
                                        DatabaseHelper.COL_PRICE,
                                        DatabaseHelper.COL_CURRENCY,
                                        DatabaseHelper.COL_BUY_LINK,
                                        DatabaseHelper.COL_LANG,
                                        DatabaseHelper.COL_PAGE_COUNT,
                                        DatabaseHelper.COL_RATING,
                                        DatabaseHelper.COL_RATING_COUNT,
                                        DatabaseHelper.COL_IS_BOOKMARK };

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.BOOKMARKTBL, columns,
                                    null,null,null,null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }
}
