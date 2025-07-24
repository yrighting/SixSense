package com.example.bungeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyParty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_party)

        val recyclerView = findViewById<RecyclerView>(R.id.myPartyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PartyListAdapter(PartyStorage.getMyParties()) {}
        recyclerView.adapter = adapter
    }
}