package com.example.sixsense.data.entity.Restaurant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val category: String,
    val rating: Float,
    val imageUrl: String?,
    val description: String,
    val isSaleAvailable: Boolean
)

