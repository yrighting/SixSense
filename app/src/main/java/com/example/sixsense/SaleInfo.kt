package com.example.sixsense

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.sixsense.app.R
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
                imgPhoto.visibility = View. GONE         // 이미지가 없을 경우 감춤

            }
        }

        // 좋아요 버튼 클릭 시 좋아요 수 증가
        imageLike.setOnClickListener {
            currentLikeCount += 1
            textLikeCount.text = "$currentLikeCount"
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
                        }
                        tagContainer.addView(tagView)
                    }
                }
            }
        }

    }

}
