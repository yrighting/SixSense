package com.example.sixsense.party

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sixsense.R

// 전체 파티 목록을 RecyclerView로 보여주는 화면
class PartyList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PartyListAdapter
    private lateinit var partyDataList: List<PartyData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_list)

        // 더미 데이터를 실제 데이터 대신 사용
        partyDataList = DummyData.getParties()

        recyclerView = findViewById(R.id.recyclerViewPartyList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 어댑터에 리스트와 클릭 콜백 전달
        adapter = PartyListAdapter(partyDataList) { party ->
            val intent = Intent(this, PartyDetailActivity::class.java)
            intent.putExtra("partyId", party.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}
