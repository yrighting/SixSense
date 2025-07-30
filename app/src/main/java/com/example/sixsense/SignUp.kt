package com.sixsense.app

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtId: EditText
    private lateinit var edtPw: EditText
    private lateinit var edtPwCheck: EditText
    private lateinit var btnSign: Button
    private lateinit var btnCancel: Button

    private lateinit var dbManager: DBManager
    private lateinit var sqlDB: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initViews()
        dbManager = DBManager(this, "groupTBL", null, 1)

        btnSign.setOnClickListener {
            handleSignUp()
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initViews() {
        edtName = findViewById(R.id.edtName)
        edtId = findViewById(R.id.edtId)
        edtPw = findViewById(R.id.edtPw)
        edtPwCheck = findViewById(R.id.edtPwCheck)
        btnSign = findViewById(R.id.btnSign)
        btnCancel = findViewById(R.id.btnCancel)
    }

    private fun handleSignUp() {
        val name = edtName.text.toString().trim()
        val id = edtId.text.toString().trim()
        val pw = edtPw.text.toString()
        val pwCheck = edtPwCheck.text.toString()

        when {
            name.isEmpty() || id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty() -> {
                showToast("모든 항목을 입력해주세요.")
                return
            }
            name.length < 2 -> {
                showToast("이름을 2자리 이상 입력하세요.")
                return
            }
            id.length < 6 -> {
                showToast("아이디를 6자리 이상 입력하세요.")
                return
            }
            !isValidPassword(pw) -> {
                showToast("비밀번호는 영문자, 숫자, 특수문자를 포함한 6자리 이상이어야 합니다.")
                return
            }
            pw != pwCheck -> {
                showToast("비밀번호가 일치하지 않습니다.")
                return
            }
        }

        sqlDB = dbManager.writableDatabase

        if (isDuplicate("gName", name)) {
            showToast("이미 사용 중인 닉네임입니다.")
            return
        }

        if (isDuplicate("gID", id)) {
            showToast("이미 사용 중인 아이디입니다.")
            return
        }

        sqlDB.execSQL("INSERT INTO groupTBL VALUES (?, ?, ?)", arrayOf(name, id, pw))
        sqlDB.close()

        showToast("회원가입이 완료되었습니다.")
        startActivity(Intent(this, SignIn::class.java))
        finish()
    }

    private fun isValidPassword(password: String): Boolean {
        // 영문자, 숫자, 특수문자 조합 6자 이상
        val passwordPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{6,}$")
        return passwordPattern.matches(password)
    }

    private fun isDuplicate(field: String, value: String): Boolean {
        val cursor = sqlDB.rawQuery("SELECT $field FROM groupTBL WHERE $field=?", arrayOf(value))
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    class DBManager(
        context: Context,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(
                """
                CREATE TABLE IF NOT EXISTS groupTBL (
                    gName TEXT NOT NULL,
                    gID TEXT NOT NULL,
                    gPass TEXT NOT NULL
                )
                """.trimIndent()
            )
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(db)
        }
    }
}
