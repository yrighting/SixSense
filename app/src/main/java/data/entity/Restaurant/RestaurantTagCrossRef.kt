package com.sixsense.app.data.entity

import androidx.room.*

@Entity(primaryKeys = ["restaurantId", "tagId"],
    indices = [Index(value = ["tagId"]), Index(value = ["restaurantId"])]
)
data class RestaurantTagCrossRef(
    val restaurantId: Int,
    val tagId: Int
)
