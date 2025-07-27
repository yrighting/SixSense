package data.entity.salespost

data class SalesPost(
    val restaurantId: String,      // 식당 이름
    val aliasId: String,          //작성자 가명 ID
    val title: String,            //게시글 제목
    val content: String,          // 게시글 본문 내용
    val timestamp: Long,          // 작성 시간 (밀리초 단위)
    var likeCount: Int = 0,       // 좋아요 수
    val imageResId: Int? = null,     // 샘플용 리소스 이미지
    val imageUri: String? = null     // 사용자 업로드 이미지
)
