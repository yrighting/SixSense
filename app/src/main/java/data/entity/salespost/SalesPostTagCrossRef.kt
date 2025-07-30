package data.entity.salespost

import androidx.room.Entity

@Entity(primaryKeys = ["postId", "tagId"])
data class SalesPostTagCrossRef(
    val postId: Int,
    val tagId: Int
)
