package com.example.sixsense

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SignUp : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var btnRegist: Button
    lateinit var btnIdCheck: Button
    lateinit var btnPwCheck: ImageButton

    lateinit var edtName: EditText
    lateinit var edtBirth: EditText
    lateinit var edtId: EditText
    lateinit var edtPw: EditText
    lateinit var edtPwConfirm: EditText
    lateinit var edtSchool: EditText
    lateinit var edtMajor: EditText
    lateinit var edtSchNum: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtName = findViewById(R.id.edtName)
        edtBirth = findViewById(R.id.edtBirth)
        edtId = findViewById(R.id.edtId)
        edtPw = findViewById(R.id.edtPw)
        edtPwConfirm = findViewById(R.id.edtPwConfirm)
        edtSchool = findViewById(R.id.edtSchool)
        edtMajor = findViewById(R.id.edtMajor)
        edtSchNum = findViewById(R.id.edtSchNum)

        btnRegist = findViewById(R.id.btnRegist)
        btnIdCheck = findViewById(R.id.btnIdCheck)
        btnPwCheck = findViewById(R.id.btnPwCheck)

        dbManager = DBManager(this, "personDB", null, 1)

        btnRegist.setOnClickListener{
            var str_name: String = edtName.text.toString()
            var str_birth: String = edtBirth.text.toString()
            var str_id: String = edtId.text.toString()
            var str_pw: String = edtPw.text.toString()
            var str_pwconfirm: String = edtPwConfirm.text.toString()
            var str_school: String = edtSchool.text.toString()
            var str_major: String = edtMajor.text.toString()
            var str_schnum: String = edtSchNum.text.toString()

            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL(
                "INSERT INTO personDB (name, birth, id, pw, school, major, schnum) VALUES ('" +
                        str_name + "', '" + str_birth + "', '" + str_id + "', '" + str_pw + "', '" +
                        str_school + "', '" + str_major + "', '" + str_schnum + "')"
            )
            sqlitedb.close()

            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }
}