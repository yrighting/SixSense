import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sixsense.R
import com.example.sixsense.SignIn

class SignUpActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtId: EditText
    lateinit var edtPw: EditText
    lateinit var edtPwCheck: EditText
    lateinit var btnSign: Button
    lateinit var dbManager: DBManager
    lateinit var sqlDB: SQLiteDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtName = findViewById(R.id.edtName)
        edtId = findViewById(R.id.edtId)
        edtPw = findViewById(R.id.edtPw)
        edtPwCheck = findViewById(R.id.edtPwCheck)
        btnSign = findViewById(R.id.btnSign)

        dbManager = DBManager(this, "groupTBL", null, 1)

        btnSign.setOnClickListener {
            val name = edtName.text.toString()
            val id = edtId.text.toString()
            val pw = edtPw.text.toString()
            val pwCheck = edtPwCheck.text.toString()

            if (name.isBlank() || id.isBlank() || pw.isBlank() || pwCheck.isBlank()) {
                showToast("이름, 아이디와 비밀번호를 모두 입력해주세요.")
                return@setOnClickListener
            }

            if (name.length < 2) {
                showToast("이름을 2자리 이상 입력하세요.")
                return@setOnClickListener
            }

            if (id.length < 6) {
                showToast("아이디를 6자리 이상 입력하세요.")
                return@setOnClickListener
            }

            if (pw.length < 6) {
                showToast("비밀번호를 6자리 이상 입력하세요.")
                return@setOnClickListener
            }

            if (pw != pwCheck) {
                showToast("비밀번호가 다릅니다. 다시 입력하세요.")
                return@setOnClickListener
            }

            sqlDB = dbManager.writableDatabase

            val nameCursor = sqlDB.rawQuery("SELECT gName FROM groupTBL WHERE gName=?", arrayOf(name))
            if (nameCursor.moveToFirst()) {
                showToast("이미 사용 중인 닉네임입니다.")
                nameCursor.close()
                return@setOnClickListener
            }
            nameCursor.close()

            val idCursor = sqlDB.rawQuery("SELECT gID FROM groupTBL WHERE gID=?", arrayOf(id))
            if (idCursor.moveToFirst()) {
                showToast("이미 사용 중인 아이디입니다.")
                idCursor.close()
                return@setOnClickListener
            }
            idCursor.close()

            sqlDB.execSQL("INSERT INTO groupTBL VALUES (?, ?, ?)", arrayOf(name, id, pw))
            sqlDB.close()

            showToast("회원가입이 완료되었습니다.")
            startActivity(Intent(this, SignIn::class.java))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    class DBManager(
        context: Context,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL("""
                CREATE TABLE IF NOT EXISTS groupTBL (
                    gName CHAR(20) NOT NULL,
                    gID CHAR(30) NOT NULL,
                    gPass CHAR(30) NOT NULL
                )
            """.trimIndent())
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(db)
        }
    }
}
