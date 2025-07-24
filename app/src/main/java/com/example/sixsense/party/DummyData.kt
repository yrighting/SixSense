package com.example.sixsense.party

object DummyData {
    private val parties = mutableListOf(
        PartyData(1, "맛집 모임", "2025-08-01", "서울 강남", 5, "설명", "주최자"),
        PartyData(2, "등산 모임", "2025-08-03", "서울 북한산", 8, "설명", "주최자")
    )

    private val comments = mutableListOf<Comment>(
        Comment(1, 1, "익명", "좋은 모임이네요!", "2025-07-24 10:00"),
        Comment(2, 1, "사용자1", "참여하고 싶어요", "2025-07-24 10:30")
    )

    fun getParties(): List<PartyData> = parties

    fun getCommentsForParty(partyId: Int): MutableList<Comment> {
        return comments.filter { it.partyId == partyId }.toMutableList()
    }
}
