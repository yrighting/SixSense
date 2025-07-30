package com.sixsense.app


data class Review(
    val text: String,
    val tags: List<String> = emptyList(),
    val userName: String = "익명",
    val rating: Float = 4.0f,
    val date: String = ""
)