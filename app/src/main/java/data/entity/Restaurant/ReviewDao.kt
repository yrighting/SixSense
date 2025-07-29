package com.sixsense.app.data.dao

import androidx.room.*
import com.sixsense.app.data.entity.*

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: Review)

    @Query("SELECT * FROM reviews WHERE restaurantId = :restaurantId ORDER BY timestamp DESC")
    suspend fun getReviewsForRestaurant(restaurantId: Int): List<Review>
}