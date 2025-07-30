package data.entity.salespost

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales_posts")
data class SalesPost(
    @PrimaryKey(autoGenerate = true) val postId: Int = 0,
    val restaurantId: String,
    val aliasId: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    val likeCount: Int = 0,
    val imageResId: Int? = null,
    val imageUri: String? = null,

    @ColumnInfo(name = "latitude")
    val latitude: Double? = null,

    @ColumnInfo(name = "longitude")
    val longitude: Double? = null
)


