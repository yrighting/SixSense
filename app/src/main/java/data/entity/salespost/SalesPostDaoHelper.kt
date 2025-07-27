package data.entity.salespost

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.sixsense.data.sqlite.SalesPostDbHelper

// SalesPost 테이블에 접근하는 DAO 역할 클래스
class SalesPostDaoHelper(private val context: Context) {
    private val dbHelper = SalesPostDbHelper(context)    // DB 접근을 위한 SQLiteOpenHelper 객체

    // 게시글 1개 삽입
    fun insert(post: SalesPost) {
        val db = dbHelper.writableDatabase

        // ContentValues를 이용해 key-value 형태로 데이터 입력
        val values = ContentValues().apply {
            put("restaurantId", post.restaurantId)
            put("aliasId", post.aliasId)
            put("title", post.title)
            put("content", post.content)
            put("timestamp", post.timestamp)
            put("likeCount", post.likeCount)
            put("imageResId", post.imageResId) // null 가능
            put("imageUri", post.imageUri) // null 가능
        }
        // insert(table명, nullColumnHack, values)
        db.insert("sales_posts", null, values)
        db.close()
    }

    // 전체 게시글 조회
    fun getAllSalesPosts(): List<SalesPost> {
        val posts = mutableListOf<SalesPost>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM sales_posts ORDER BY timestamp DESC", null)

        if (cursor.moveToFirst()) {
            do {
                // imageResId 컬럼 → null 처리
                val imageResIdIndex = cursor.getColumnIndex("imageResId")
                val imageResId = if (!cursor.isNull(imageResIdIndex)) cursor.getInt(imageResIdIndex) else null

                // imageUri 컬럼 → null 처리
                val imageUriIndex = cursor.getColumnIndex("imageUri")
                val imageUri = if (!cursor.isNull(imageUriIndex)) cursor.getString(imageUriIndex) else null

                // 커서로부터 SalesPost 객체 생성
                val post = SalesPost(
                    restaurantId = cursor.getString(cursor.getColumnIndexOrThrow("restaurantId")),
                    aliasId = cursor.getString(cursor.getColumnIndexOrThrow("aliasId")),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    content = cursor.getString(cursor.getColumnIndexOrThrow("content")),
                    timestamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp")),
                    likeCount = cursor.getInt(cursor.getColumnIndexOrThrow("likeCount")),
                    imageResId = imageResId,
                    imageUri = imageUri
                )
                posts.add(post)
            } while (cursor.moveToNext())    // 다음 행으로 이동
        }
        // 커서와 DB 닫기
        cursor.close()
        db.close()
        return posts
    }
}
