package com.sixsense.app

data class Restaurant(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    var rating: Double = 0.0,
    var trust: Double = 0.0,
    val category: String, // 예: "한식", "중식"
    val reviews: MutableList<Review> = mutableListOf()
)
