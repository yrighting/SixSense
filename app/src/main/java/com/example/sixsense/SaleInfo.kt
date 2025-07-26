package com.example.sixsense

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class SaleInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_info)

        val txtRestaurantName = findViewById<TextView>(R.id.txtRestaurantName)
        val txtWriter = findViewById<TextView>(R.id.txtWriter)
        val txtTime = findViewById<TextView>(R.id.txtTime)
        val txtContent = findViewById<TextView>(R.id.txtContent)
        val imgPhoto = findViewById<ImageView>(R.id.imgPhoto)
        val imageLike = findViewById<ImageView>(R.id.image_like)
        val textLikeCount = findViewById<TextView>(R.id.text_like_count)

        // 인텐트로부터 데이터 받기
        val restaurantName = intent.getStringExtra("restaurantName")
        val writerId = intent.getStringExtra("writerId")
        val time = intent.getStringExtra("time")
        val content = intent.getStringExtra("content")
        val imageUri = intent.getStringExtra("imageUri")
        val imageResId = intent.getIntExtra("imageResId", -1)
        var currentLikeCount = intent.getIntExtra("likeCount", 0)

        // 텍스트 데이터 적용
        txtRestaurantName.text = restaurantName
        txtWriter.text = writerId
        txtTime.text = time
        txtContent.text = content
        textLikeCount.text = "$currentLikeCount"

        // 이미지 적용
        when {
            !imageUri.isNullOrEmpty() -> {
                Glide.with(this)
                    .load(Uri.parse(imageUri))
                    .error(R.drawable.placeholder_image)
                    .into(imgPhoto)
            }
            imageResId > 0 -> {
                imgPhoto.setImageResource(imageResId)
            }
            else -> {
                imgPhoto.setImageResource(R.drawable.placeholder_image)
            }
        }

        // 좋아요 버튼
        imageLike.setOnClickListener {
            currentLikeCount += 1
            textLikeCount.text = "$currentLikeCount"
        }
    }

}
