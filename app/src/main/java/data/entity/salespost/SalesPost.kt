package data.entity.salespost

data class SalesPost(
    val postId: Int,
    val restaurantId: String,
    val aliasId: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    var likeCount: Int = 0
)
