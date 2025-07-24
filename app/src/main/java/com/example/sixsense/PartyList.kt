package com.example.bungeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PartyList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PartyListAdapter
    private lateinit var partyList: List<Party>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_list)

        partyList = DummyData.getParties()  // 더미 데이터

        recyclerView = findViewById(R.id.partyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PartyListAdapter(partyList) { party ->
            val intent = Intent(this, PartyDetail::class.java)
            intent.putExtra("partyId", party.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}