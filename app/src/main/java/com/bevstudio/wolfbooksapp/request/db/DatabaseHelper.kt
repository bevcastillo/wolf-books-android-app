package com.bevstudio.wolfbooksapp.request.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bevstudio.wolfbooksapp.model.db.VolumeBooks

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE, null, 1) {
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(" DROP TABLE IF EXISTS '$BOOKMARKTBL' ")
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_CONTACTS)
    }

    fun addBookmark(volumeBooks: VolumeBooks) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_ID, volumeBooks.str_id)
        values.put(COL_VOL_ID, volumeBooks.volumeId)
        values.put(COL_IS_BOOKMARK, volumeBooks.isBookmark)

        db.insert(BOOKMARKTBL, null, values)
        db.close()
    }

    //getting the VolumeId
    @SuppressLint("Range")
    fun getVolumeId(volumeId: String): ArrayList<VolumeBooks> {
        val volumeBooksArrayList = ArrayList<VolumeBooks>()

        val selectQuery =
            " SELECT * FROM $BOOKMARKTBL WHERE $COL_VOL_ID = $volumeId"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val volumeBooks = VolumeBooks()
                volumeBooks.volumeId = cursor.getString(cursor.getColumnIndex(COL_VOL_ID))
                volumeBooksArrayList.add(volumeBooks)
            } while (cursor.moveToNext())
        }
        return volumeBooksArrayList
    }

    val all: ArrayList<VolumeBooks>
        //displaying all data
        @SuppressLint("Range")
        get() {
            val volumeBooksArrayList = ArrayList<VolumeBooks>()

            val selectQuery = "SELECT * FROM $BOOKMARKTBL"
            val db = this.readableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            //
            if (cursor.moveToFirst()) {
                do {
                    val volumeBooks = VolumeBooks()
                    volumeBooks.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                    volumeBooks.volumeId = cursor.getString(cursor.getColumnIndex(COL_VOL_ID))
                    volumeBooks.isBookmark =
                        cursor.getString(cursor.getColumnIndex(COL_IS_BOOKMARK)).toBoolean()
                    volumeBooksArrayList.add(volumeBooks)
                } while (cursor.moveToNext())
            }

            return volumeBooksArrayList
        }


    fun removeBookmark(id: Int) {
        val db = this.writableDatabase
        db.delete(BOOKMARKTBL, "$COL_ID = ? ", arrayOf(id.toString()))
    }


    companion object {
        const val DATABASE: String = "bookmarksdb"
        const val BOOKMARKTBL: String = "bookmarks_tbl"
        const val COL_ID: String = "ID"
        const val COL_VOL_ID: String = "volume_id"
        const val COL_IS_BOOKMARK: String = "is_bookmark"

        private const val CREATE_TABLE_CONTACTS = ("CREATE TABLE "
                + BOOKMARKTBL
                + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_VOL_ID + " TEXT, "
                + COL_IS_BOOKMARK + " BOOLEAN );")
    }
}
