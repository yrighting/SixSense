package com.example.sixsense.party

// 댓글 데이터 정보를 담는 데이터 클래스
data class Comment(
    val id: Int = 0,
    val partyId: Int,     // 어떤 Party(Post)에 달린 댓글인지 연결용 ID
    val author: String,   // 작성자 이름
    val content: String,  // 댓글 내용
    val timestamp: String // 작성 시간 (문자열)
)
