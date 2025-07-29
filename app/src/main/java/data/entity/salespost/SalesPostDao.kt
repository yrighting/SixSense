package data.entity.salespost

import androidx.room.*

@Dao
interface SalesPostDao {
    @Insert
    suspend fun insert(post: SalesPost): Long

    @Query("SELECT * FROM sales_posts ORDER BY timestamp DESC")
    suspend fun getAllSalesPosts(): List<SalesPost>

    @Insert
    suspend fun insertSalesPostTagCrossRef(crossRef: SalesPostTagCrossRef)

    @Transaction
    @Query("SELECT * FROM sales_posts WHERE postId = :postId")
    suspend fun getSalesPostWithTags(postId: Int): SalesPostWithTags
}
