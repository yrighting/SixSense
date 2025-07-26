package data.entity.salespost

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.sixsense.data.sqlite.SalesPostDbHelper

class SalesPostDaoHelper(private val context: Context) {
    private val dbHelper = SalesPostDbHelper(context)

    // Insert
    fun insert(post: SalesPost) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("restaurantId", post.restaurantId)
            put("aliasId", post.aliasId)
            put("title", post.title)
            put("content", post.content)
            put("timestamp", post.timestamp)
            put("likeCount", post.likeCount)
            put("imageResId", post.imageResId)
        }
        db.insert("sales_posts", null, values)
        db.close()
    }

    // 전체 글 가져오기
    fun getAllSalesPosts(): List<SalesPost> {
        val posts = mutableListOf<SalesPost>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM sales_posts ORDER BY timestamp DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val post = SalesPost(
                    restaurantId = cursor.getString(cursor.getColumnIndexOrThrow("restaurantId")),
                    aliasId = cursor.getString(cursor.getColumnIndexOrThrow("aliasId")),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    content = cursor.getString(cursor.getColumnIndexOrThrow("content")),
                    timestamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp")),
                    likeCount = cursor.getInt(cursor.getColumnIndexOrThrow("likeCount")),
                    imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"))

                )
                posts.add(post)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return posts
    }
}
