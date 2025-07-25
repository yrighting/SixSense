package com.example.sixsense

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val loginButton = findViewById<Button>(R.id.loginbutton)
        var str_id: String = edtId.text.toString()

        loginButton.setOnClickListener{
            val id = findViewById<EditText>(R.id.idEditText).text.toString()
            val pw = findViewById<EditText>(R.id.pwEditTextPassword).text.toString()

            if(id.isNotEmpty()){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userId", str_id)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}