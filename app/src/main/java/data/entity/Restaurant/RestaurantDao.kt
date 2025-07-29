package com.sixsense.app.data.dao

import androidx.room.*
import com.sixsense.app.data.entity.*

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: RestaurantTagCrossRef)

    @Transaction
    @Query("SELECT * FROM restaurants")
    suspend fun getAllRestaurantsWithTags(): List<RestaurantWithTags>

    @Transaction
    @Query("SELECT * FROM restaurants WHERE id = :restaurantId")
    suspend fun getRestaurantDetail(restaurantId: Int): RestaurantWithTags
}