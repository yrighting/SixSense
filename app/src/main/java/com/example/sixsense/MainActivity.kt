package com.example.sixsense

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var edtId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbManager = DBManager(this, "personDB", null, 1)
        val sqlitedb = dbManager.readableDatabase

        val cursor = sqlitedb.rawQuery(
            "SELECT name, id, pw, school, major, schnum FROM personDB WHERE id = '$edtId'",
            null
        )

        if(cursor.moveToFirst()){
            val name = cursor.getString(0)
            val id = cursor.getString(1)
            val pw = cursor.getString(2)
            val school = cursor.getString(3)
            val major = cursor.getString(4)
            val schnum = cursor.getString(5)
            Log.d("MyUser", "이름: $name, 아이디: $id, 비밀번호: $pw, 학교: $school, 전공: $major, 학번: $schnum")
        } else{
            Log.d("MyUser", "해당 ID를 가진 사용자가 없습니다.")
        }

        cursor.close()
        sqlitedb.close()
    }
}