package com.example.sixsense.data.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SalesPostDbHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "sales_post.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "sales_posts"
        const val COLUMN_ID = "postId"
        const val COLUMN_RESTAURANT_ID = "restaurantId"
        const val COLUMN_ALIAS_ID = "aliasId"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_TIMESTAMP = "timestamp"
        const val COLUMN_LIKE_COUNT = "likeCount"

        private const val SQL_CREATE_ENTRIES =
            """CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RESTAURANT_ID TEXT,
                $COLUMN_ALIAS_ID TEXT,
                $COLUMN_TITLE TEXT,
                $COLUMN_CONTENT TEXT,
                $COLUMN_TIMESTAMP INTEGER,
                $COLUMN_LIKE_COUNT INTEGER DEFAULT 0
            )"""

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
