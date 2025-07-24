package com.example.sixsense.party

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.sixsense.R

// 파티 생성 화면 - 사용자로부터 파티 정보를 입력받아 등록 처리(현재 DB 저장 미구현)
class CreateParty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_party)

        // 입력 필드들 연결
        val nameInput = findViewById<EditText>(R.id.inputPartyName)
        val timeInput = findViewById<EditText>(R.id.inputPartyTime)
        val locationInput = findViewById<EditText>(R.id.inputPartyLocation)
        val descInput = findViewById<EditText>(R.id.inputPartyDescription)

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            // TODO: 실제 DB 저장 로직 추가 필요
            // 임시로 PartyList 화면으로 이동만 함
            val intent = Intent(this, PartyList::class.java)
            startActivity(intent)
        }
    }
}
