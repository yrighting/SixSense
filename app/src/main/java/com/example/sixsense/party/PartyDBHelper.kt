package com.example.sixsense.party

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// SQLite DB를 통해 파티와 댓글 데이터 관리 담당
class PartyDBHelper(context: Context) :
    SQLiteOpenHelper(context, "partyDB", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        // Party 테이블 생성 쿼리
        val createPartyTable = """
            CREATE TABLE Party (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                description TEXT,
                date TEXT,
                personnel INTEGER
            )
        """.trimIndent()

        // Comment 테이블 생성 쿼리 (partyId 외래키 포함)
        val createCommentTable = """
            CREATE TABLE Comment (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                partyId INTEGER,
                author TEXT,
                content TEXT,
                timestamp TEXT,
                FOREIGN KEY(partyId) REFERENCES Party(id)
            )
        """.trimIndent()

        db.execSQL(createPartyTable)
        db.execSQL(createCommentTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 기존 테이블 삭제 후 재생성
        db.execSQL("DROP TABLE IF EXISTS Party")
        db.execSQL("DROP TABLE IF EXISTS Comment")
        onCreate(db)
    }

    // 새로운 댓글을 DB에 삽입하는 함수
    fun insertComment(partyId: Int, author: String, content: String, timestamp: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("partyId", partyId)
            put("author", author)
            put("content", content)
            put("timestamp", timestamp)
        }
        db.insert("Comment", null, values)
        db.close()
    }

    // 특정 파티에 달린 댓글 리스트를 가져오는 함수
    fun getCommentsForParty(partyId: Int): List<Comment> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM Comment WHERE partyId = ? ORDER BY id DESC",
            arrayOf(partyId.toString())
        )
        val comments = mutableListOf<Comment>()

        while (cursor.moveToNext()) {
            val comment = Comment(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                partyId = cursor.getInt(cursor.getColumnIndexOrThrow("partyId")),
                author = cursor.getString(cursor.getColumnIndexOrThrow("author")),
                content = cursor.getString(cursor.getColumnIndexOrThrow("content")),
                timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"))
            )
            comments.add(comment)
        }

        cursor.close()
        db.close()
        return comments
    }
}
