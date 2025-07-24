package data.entity.salespost

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales_posts")
data class SalesPost(
    @PrimaryKey(autoGenerate = true) val postId: Int,
    val restaurantId: Int,
    val aliasId: String,
    val content: String,
    val timestamp: Long,
    var likeCount: Int = 0
)
