package com.example.sixsense

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SaleInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_info)

        // 뷰 연결
        val txtRestaurantName = findViewById<TextView>(R.id.txtRestaurantName)
        val txtWriter = findViewById<TextView>(R.id.txtWriter)
        val txtTime = findViewById<TextView>(R.id.txtTime)
        val txtContent = findViewById<TextView>(R.id.txtContent)
        val txtLikeCount = findViewById<TextView>(R.id.txtLikeCount)
        //val imgPhoto = findViewById<ImageView>(R.id.imgPhoto)

        // 인텐트로부터 데이터 받기
        val restaurantName = intent.getStringExtra("restaurantName")
        val writerId = intent.getStringExtra("writerId")
        val time = intent.getStringExtra("time")
        val content = intent.getStringExtra("content")
        val likeCount = intent.getIntExtra("likeCount", 0)

        // 받아온 데이터 적용
        txtRestaurantName.text = restaurantName
        txtWriter.text = writerId
        txtTime.text = time
        txtContent.text = content
        txtLikeCount.text = "좋아요: $likeCount"


    }
}
