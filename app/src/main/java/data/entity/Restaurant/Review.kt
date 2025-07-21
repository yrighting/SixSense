package com.sixsense.app.data.entity

import androidx.room.*

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val restaurantId: Int,
    val userName: String,
    val rating: Float,
    val comment: String,
    val timestamp: Long
)
