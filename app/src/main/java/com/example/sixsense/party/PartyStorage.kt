package com.example.sixsense.party

// 메모리 기반 파티 데이터 저장소 (임시 구현)
// 실제 DB 연동 시 해당 부분 교체 필요
object PartyStorage {
    private val parties = mutableListOf<PartyData>()
    private val joinedParties = mutableListOf<PartyData>()

    fun getAllParties(): List<PartyData> = parties

    fun addParty(partyData: PartyData) {
        parties.add(partyData)
    }

    fun getPartyById(id: Int): PartyData? = parties.find { it.id == id }

    fun joinParty(partyData: PartyData) {
        if (!joinedParties.contains(partyData)) {
            joinedParties.add(partyData)
        }
    }

    fun getMyParties(): List<PartyData> = joinedParties
}
