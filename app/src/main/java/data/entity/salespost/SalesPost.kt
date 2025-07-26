package data.entity.salespost

data class SalesPost(
    val restaurantId: String,
    val aliasId: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    var likeCount: Int = 0,
    val imageResId: Int? = null,     // 샘플용 리소스 이미지
    val imageUri: String? = null     // 사용자 업로드 이미지
)
