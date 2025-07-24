package com.example.bungeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PartyChatRedirect : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chatRoomId = intent.getStringExtra("chatRoomId")

        // 자체 채팅 화면으로 이동 (예: ChatRoomActivity)
        val intent = Intent(this, ChatRoomActivity::class.java)
        intent.putExtra("chatRoomId", chatRoomId)
        startActivity(intent)
        finish()
    }
}