package com.sixsense.app

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignIn : AppCompatActivity() {

    private lateinit var dbManager: SignUpActivity.DBManager
    private lateinit var sqlDB: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btnSignUp = findViewById<Button>(R.id.btnSignUpTo)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val edtId = findViewById<EditText>(R.id.edtSignInId)
        val edtPw = findViewById<EditText>(R.id.edtSignInPw)

        dbManager = SignUpActivity.DBManager(this, "groupTBL", null, 1)
        sqlDB = dbManager.readableDatabase

        btnSignIn.setOnClickListener {
            val id = edtId.text.toString().trim()
            val pw = edtPw.text.toString().trim()

            if (id.isEmpty() || pw.isEmpty()) {
                showToast("아이디와 비밀번호를 입력하세요.")
                return@setOnClickListener
            }

            Thread {
                val cursor = sqlDB.rawQuery(
                    "SELECT gName FROM groupTBL WHERE gID = ? AND gPass = ?",
                    arrayOf(id, pw)
                )

                if (cursor.moveToFirst()) {
                    val userName = cursor.getString(0)
                    cursor.close()

                    // UI 작업은 runOnUiThread에서
                    runOnUiThread {
                        val intent = Intent(this, MainActivity::class.java).apply {
                            putExtra("userId", id)
                            putExtra("userName", userName)
                        }
                        startActivity(intent)
                        finish()
                    }
                } else {
                    cursor.close()
                    runOnUiThread {
                        showToast("아이디 또는 비밀번호가 올바르지 않습니다.")
                    }
                }
            }.start()
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
