package com.example.sixsense.party

// 파티 모임의 핵심 정보 담는 데이터 클래스
data class PartyData(
    val id: Int,            // 파티 고유 아이디
    val title: String,      // 파티명
    val date: String,       // 날짜
    val location: String,   // 장소
    val personnel: Int,     // 인원수
    val description: String,// 상세 설명
    val host: String        // 주최자명
)

