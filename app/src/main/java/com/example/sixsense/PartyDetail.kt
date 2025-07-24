package com.example.bungeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PartyDetail : AppCompatActivity() {
    private lateinit var party: Party

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_detail)

        val partyId = intent.getIntExtra("partyId", -1)
        party = DummyData.getPartyById(partyId)

        findViewById<TextView>(R.id.partyTitle).text = party.title
        findViewById<TextView>(R.id.partyTime).text = party.time
        findViewById<TextView>(R.id.partyLocation).text = party.location
        findViewById<TextView>(R.id.partyDescription).text = party.description
        findViewById<TextView>(R.id.partyHost).text = party.host

        findViewById<Button>(R.id.joinButton).setOnClickListener {
            PartyStorage.joinParty(party)
            val intent = Intent(this, MyParty::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.goToChatButton).setOnClickListener {
            val intent = Intent(this, PartyChatRedirect::class.java)
            intent.putExtra("chatRoomId", party.chatRoomId)
            startActivity(intent)
        }
    }
}