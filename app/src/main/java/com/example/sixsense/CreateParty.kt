package com.example.lightningparty

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CreateParty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_party)

        val nameInput = findViewById<EditText>(R.id.inputPartyName)
        val timeInput = findViewById<EditText>(R.id.inputPartyTime)
        val locationInput = findViewById<EditText>(R.id.inputPartyLocation)
        val descInput = findViewById<EditText>(R.id.inputPartyDescription)
        val chatUrlInput = findViewById<EditText>(R.id.inputPartyChatUrl)

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            // DB 저장 로직 생략
            val intent = Intent(this, PartyList::class.java)
            startActivity(intent)
        }
    }
}