package com.sixsense.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sixsense.app.com.example.sixsense.CreateParty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMatzip = findViewById<Button>(R.id.btnMatzip)
        val btnParty = findViewById<Button>(R.id.btnParty)
        val btnBoard = findViewById<Button>(R.id.btnBoard)

        btnMatzip.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        btnParty.setOnClickListener {
            val intent = Intent(this, CreateParty::class.java)
            startActivity(intent)
        }

        btnBoard.setOnClickListener {
            val intent = Intent(this, SaleMain::class.java)
            startActivity(intent)
        }
    }
}
