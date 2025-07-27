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

    // 앱에서 DB 처음 생성될 때 실행됨
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    // DB 버전 업그레이드 시 기존 테이블 삭제 후 재생성
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)    // 기존 테이블 삭제
        onCreate(db)     // 새 테이블 생성
    }

    companion object {
        const val DATABASE_NAME = "sales_post.db"      // DB 파일 이름
        const val DATABASE_VERSION = 1                 // DB 버전 (올리면 onUpgrade 호출됨)

        // 테이블명 및 컬럼 상수 정의
        const val TABLE_NAME = "sales_posts"
        const val COLUMN_RESTAURANT_ID = "restaurantId"
        const val COLUMN_ALIAS_ID = "aliasId"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_TIMESTAMP = "timestamp"
        const val COLUMN_LIKE_COUNT = "likeCount"
        const val COLUMN_IMAGE_RES_ID = "imageResId"

        // 테이블 생성 SQL
        private const val SQL_CREATE_ENTRIES =
            """CREATE TABLE $TABLE_NAME (
                INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RESTAURANT_ID TEXT,
                $COLUMN_ALIAS_ID TEXT,
                $COLUMN_TITLE TEXT,
                $COLUMN_CONTENT TEXT,
                $COLUMN_TIMESTAMP INTEGER,
                $COLUMN_LIKE_COUNT INTEGER DEFAULT 0,
                $COLUMN_IMAGE_RES_ID INTEGER,
                imageUri Text
            )"""

        // 테이블 삭제 SQL (업그레이드 시 호출됨)
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
