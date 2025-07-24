package data.entity.salespost

import androidx.room.*

@Dao
interface SalesPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(salesPost: SalesPost)

    @Query("SELECT * FROM sales_posts ORDER BY timestamp DESC")
    fun getAllPosts(): List<SalesPost>

    @Query("SELECT * FROM sales_posts WHERE restaurantId = :restaurantId")
    fun getPostsByRestaurant(restaurantId: Int): List<SalesPost>

    @Query("SELECT * FROM sales_posts WHERE content LIKE '%' || :keyword || '%' ORDER BY timestamp DESC")
    fun searchPosts(keyword: String): List<SalesPost>

    @Delete
    fun delete(salesPost: SalesPost)
}
