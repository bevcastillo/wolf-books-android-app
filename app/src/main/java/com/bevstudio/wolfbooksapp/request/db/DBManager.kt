package com.bevstudio.wolfbooksapp.request.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class DBManager(private val context: Context) {
    private var dbHelper: DatabaseHelper? = null
    private var sqLiteDatabase: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): DBManager {
        dbHelper = DatabaseHelper(context)
        sqLiteDatabase = dbHelper!!.writableDatabase
        return this
    }

    fun close() {
        dbHelper!!.close()
    }

    fun insertBookmark(id: String?, isBookmark: Boolean) {
        val values = ContentValues()
        values.put(DatabaseHelper.COL_VOL_ID, id)
        values.put(DatabaseHelper.COL_IS_BOOKMARK, isBookmark)

        sqLiteDatabase!!.insert(DatabaseHelper.BOOKMARKTBL, null, values)
    }

    fun fetch(): Cursor? {
        val columns = arrayOf<String>(
            DatabaseHelper.COL_ID,
            DatabaseHelper.COL_VOL_ID,
            DatabaseHelper.COL_IS_BOOKMARK
        )

        val cursor = sqLiteDatabase!!.query(
            DatabaseHelper.BOOKMARKTBL, columns,
            null, null, null, null, null
        )

        cursor?.moveToFirst()

        return cursor
    }
}
