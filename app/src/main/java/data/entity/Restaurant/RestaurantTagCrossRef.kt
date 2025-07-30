package com.sixsense.app.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["restaurantId", "tagId"])
data class RestaurantTagCrossRef(
    val restaurantId: Int,
    val tagId: Int
)