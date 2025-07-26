package data.entity.salespost

data class SalesPost(
    val restaurantId: String,
    val aliasId: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    var likeCount: Int = 0,
    val imageResId: Int
)
