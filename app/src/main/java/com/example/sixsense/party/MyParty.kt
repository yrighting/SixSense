package com.example.sixsense.party

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sixsense.R

// 사용자가 참가한 파티 목록을 보여주는 화면
class MyParty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_party)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMyParty)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // PartyStorage에서 참가한 파티 목록 가져와서 어댑터에 연결
        val adapter = PartyListAdapter(PartyStorage.getMyParties()) {}
        recyclerView.adapter = adapter
    }
}
