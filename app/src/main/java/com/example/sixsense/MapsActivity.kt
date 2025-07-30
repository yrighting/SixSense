package com.sixsense.app

import android.widget.Spinner
import android.view.View
import android.widget.AdapterView
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.text.SimpleDateFormat
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var spinnerCategory: Spinner
    private val restaurantMap = mutableMapOf<Marker, Restaurant>()
    private val allRestaurants = mutableListOf<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val swu = LatLng(37.6294, 127.0906)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(swu, 15f))

        allRestaurants.addAll(
            listOf(
                Restaurant(
                    name = "고씨네 카레 서울여대점",
                    latitude = 37.625000,
                    longitude = 127.089358,
                    rating = 4.6,
                    trust = 95.0,
                    category = "일식",
                    reviews = mutableListOf(
                        Review("카레 향이 진하고 맛있어요!", listOf("혼밥", "신메뉴"))
                    )
                ),
                Restaurant(
                    name = "화랑대곱창",
                    latitude = 37.622502,
                    longitude = 127.083423,
                    rating = 4.4,
                    trust = 89.5,
                    category = "한식",
                    reviews = mutableListOf(
                        Review("혼자 먹기 딱 좋아요!", listOf("혼밥", "가성비"))
                    )
                ),
                Restaurant(
                    name = "육회란 연어다 본점",
                    latitude = 37.626467,
                    longitude = 127.088065,
                    rating = 4.7,
                    trust = 93.8,
                    category = "한식",
                    reviews = mutableListOf(
                        Review("양 많고 맛있어요! 연어랑도 잘 어울려요", listOf("데이트", "가성비"))
                    )
                ),
                Restaurant(
                    name = "피자얌",
                    latitude = 37.623650,
                    longitude = 127.091204,
                    rating = 4.2,
                    trust = 87.0,
                    category = "양식",
                    reviews = mutableListOf(
                        Review("반반피자 가성비 최고에요!", listOf("포장", "가성비"))
                    )
                ),
                Restaurant(
                    name = "카페인중독 노원점",
                    latitude = 37.626206,
                    longitude = 127.092402,
                    rating = 4.9,
                    trust = 97.2,
                    category = "카페",
                    reviews = mutableListOf(
                        Review("와플 진짜 부드러워요!", listOf("디저트", "조용한"))
                    )
                )
            )
        )



        showRestaurants("전체")
    }

    private fun showRestaurants(category: String) {
        map.clear()
        restaurantMap.clear()

        val filtered = if (category == "전체") allRestaurants else allRestaurants.filter { it.category == category }

        for (res in filtered) {
            val marker = map.addMarker(
                MarkerOptions()
                    .position(LatLng(res.latitude, res.longitude))
                    .title(res.name)
            )
            if (marker != null) restaurantMap[marker] = res
        }

        map.setOnMarkerClickListener { marker ->
            showReviewDialog(marker)
            true
        }
    }


    private fun showReviewDialog(marker: Marker) {
        val restaurant = restaurantMap[marker] ?: return

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_review, null)
        val textRating = dialogView.findViewById<TextView>(R.id.textRating)
        val textTrust = dialogView.findViewById<TextView>(R.id.textTrust)
        val recyclerReviews = dialogView.findViewById<RecyclerView>(R.id.recyclerReviews)
        val editNewReview = dialogView.findViewById<EditText>(R.id.editNewReview)
        val editTags = dialogView.findViewById<EditText>(R.id.editTags)
        val ratingInput = dialogView.findViewById<RatingBar>(R.id.ratingInput)

        // 평균 평점 계산 함수
        fun updateAverageRating() {
            val avg = if (restaurant.reviews.isNotEmpty()) {
                restaurant.reviews.map { it.rating }.average()
            } else 0.0
            restaurant.rating = String.format("%.1f", avg).toDouble()
            textRating.text = "평점: ${restaurant.rating}점"
        }

        updateAverageRating()
        textTrust.text = "신뢰도: ${restaurant.trust}%"

        val adapter = ReviewAdapter(restaurant.reviews)
        recyclerReviews.layoutManager = LinearLayoutManager(this)
        recyclerReviews.adapter = adapter

        val dialog = AlertDialog.Builder(this)
            .setTitle(restaurant.name)
            .setView(dialogView)
            .setPositiveButton("리뷰 등록", null)
            .setNegativeButton("닫기", null)
            .create()

        dialog.setOnShowListener {
            val registerButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            registerButton.setOnClickListener {
                val newText = editNewReview.text.toString().trim()
                val rawTags = editTags.text.toString().trim()
                val ratingValue = ratingInput.rating

                if (newText.isNotEmpty()) {
                    val tagList = rawTags
                        .split(Regex("[,\\s]+"))
                        .filter { it.startsWith("#") }
                        .map { it.removePrefix("#") }

                    val review = Review(
                        text = newText,
                        tags = tagList,
                        userName = "익명 사용자",
                        rating = ratingValue,
                        date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    )

                    restaurant.reviews.add(review)
                    adapter.notifyItemInserted(restaurant.reviews.size - 1)

                    // ✅ 평균 다시 계산 및 UI 갱신
                    updateAverageRating()

                    editNewReview.text.clear()
                    editTags.text.clear()
                    ratingInput.rating = 0f
                    Toast.makeText(this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "리뷰를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog.show()
    }

}
