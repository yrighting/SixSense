package com.sixsense.app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sixsense.app.R
import com.sixsense.app.data.entity.SixsenseDatabase
import data.entity.party.PartyPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class CreateParty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_party)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etDesc = findViewById<EditText>(R.id.etDescription)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etTime = findViewById<EditText>(R.id.etTime)
        val etCapacity = findViewById<EditText>(R.id.etCapacity)

        btnSubmit.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()
            val location = etLocation.text.toString()
            val date = etDate.text.toString()
            val time = etTime.text.toString()
            val capacityText = etCapacity.text.toString()
            val capacity = capacityText.toIntOrNull() ?: 0

            if (title.isBlank() || desc.isBlank()) {
                Toast.makeText(this, "제목과 설명을 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val party = PartyPost(
                    id = 0,
                    title = title,
                    description = desc,
                    content = desc,
                    category = "기본",
                    location = location,
                    date = date,
                    time = time,
                    maxPeople = capacity,
                    currentPeople = 1,
                    author = "익명",
                    capacity = capacity
                )

                CoroutineScope(Dispatchers.IO).launch {
                    val db = SixsenseDatabase.getDatabase(applicationContext)
                    db.partyDao().insertPartyPost(party)
                    Log.d("CreateParty", "Inserted party: $party")
                    runOnUiThread {
                        Toast.makeText(this@CreateParty, "번개팟이 등록되었습니다!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        // 날짜 선택
        etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                etDate.setText("$year-${month + 1}-$day")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // 시간 선택
        etTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(this, { _, hour, minute ->
                etTime.setText(String.format("%02d:%02d", hour, minute))
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        // 지도 연동
        val LOCATION_REQUEST_CODE = 1001
        etLocation.setOnClickListener {
            val intent = Intent(this, MapSelectActivity::class.java)
            startActivityForResult(intent, LOCATION_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            val location = data?.getStringExtra("location")
            val etLocation = findViewById<EditText>(R.id.etLocation)
            etLocation.setText(location)
        }
    }
}