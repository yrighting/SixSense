package com.sixsense.app

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.sixsense.app.data.entity.SixsenseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
        val txtTitle = findViewById<TextView>(R.id.txtPostTitle)

        // 인텐트로부터 데이터 받기
        val restaurantName = intent.getStringExtra("restaurantName")
        val writerId = intent.getStringExtra("writerId")
        val time = intent.getStringExtra("time")
        val content = intent.getStringExtra("content")
        val imageUri = intent.getStringExtra("imageUri")
        val imageResId = intent.getIntExtra("imageResId", -1)
        var currentLikeCount = intent.getIntExtra("likeCount", 0)
        val postTitle = intent.getStringExtra("title")

        // 텍스트 데이터 화면에 표시
        txtTitle.text = postTitle
        txtRestaurantName.text = restaurantName
        txtWriter.text = writerId
        txtTime.text = time
        txtContent.text = content
        textLikeCount.text = "$currentLikeCount"

        // 이미지 표시 (Uri > 리소스 ID > 기본 이미지 없음)
        when {
            !imageUri.isNullOrEmpty() -> {              // 사용자 선택 이미지 (Uri 기반)
                imgPhoto.visibility = View.VISIBLE
                Glide.with(this)
                    .load(Uri.parse(imageUri))
                    .into(imgPhoto)
            }
            imageResId > 0 -> {
                imgPhoto.visibility = View.VISIBLE
                imgPhoto.setImageResource(imageResId)          // 기본 샘플 이미지 (drawable 리소스)
            }
            else -> {
                imgPhoto.visibility = View.GONE         // 이미지가 없을 경우 감춤

            }
        }

        // 좋아요 버튼 클릭 시 좋아요 수 증가
        imageLike.setOnClickListener {
            currentLikeCount += 1
            textLikeCount.text = "$currentLikeCount"
        }

        // 버튼 눌러서 지도 보기로 이동
        val buttonViewLocation = findViewById<Button>(R.id.buttonViewLocation)
        buttonViewLocation.setOnClickListener {
            val name = restaurantName ?: return@setOnClickListener

            val lat = intent.getDoubleExtra("latitude", 0.0)
            val lng = intent.getDoubleExtra("longitude", 0.0)

            if (lat != 0.0 && lng != 0.0) {
                // 사용자가 고른 위치 정보가 있을 경우
                val intent = Intent(this, MapsActivity::class.java).apply {
                    putExtra("restaurantName", name)
                    putExtra("latitude", lat)
                    putExtra("longitude", lng)
                }
                startActivity(intent)
            } else {
                // 위치 정보가 없으면 샘플 데이터에서 찾기
                val restaurantList = listOf(
                    Restaurant("고씨네 카레 서울여대점", 37.625000, 127.089358, category = "카레"),
                    Restaurant("화랑대곱창", 37.622502, 127.083423, category = "곱창"),
                    Restaurant("육회란 연어다 본점", 37.626467, 127.088065, category = "육회"),
                    Restaurant("피자얌", 37.623650, 127.091204, category = "피자"),
                    Restaurant("카페인중독 노원점", 37.626206, 127.092402, category = "카페")
                )

                val restaurant = restaurantList.find { it.name == name }

                if (restaurant != null) {
                    val intent = Intent(this, MapsActivity::class.java).apply {
                        putExtra("restaurantName", restaurant.name)
                        putExtra("latitude", restaurant.latitude)
                        putExtra("longitude", restaurant.longitude)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "위치 정보가 없습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }


        // 태그 보여주기
        val tagContainer = findViewById<LinearLayout>(R.id.tagContainer)
        val postId = intent.getIntExtra("postId", -1)
        if (postId != -1) {
            val db = SixsenseDatabase.getDatabase(applicationContext)
            val dao = db.salesPostDao()

            // 코루틴으로 Room 접근
            CoroutineScope(Dispatchers.IO).launch {
                val postWithTags = dao.getSalesPostWithTags(postId)

                withContext(Dispatchers.Main) {
                    tagContainer.removeAllViews()
                    for (tag in postWithTags.tags) {
                        val tagView = TextView(this@SaleInfo).apply {
                            text = "#${tag.tagName}"
                            setPadding(24, 12, 24, 12)
                            background = ContextCompat.getDrawable(context, R.drawable.tag_background)
                            setTextColor(Color.BLACK)
                            textSize = 12f
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                        }
                        tagContainer.addView(tagView)
                    }
                }
            }
        }

    }

}
