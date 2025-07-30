package data.entity.salespost

import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["postId", "tagId"],
    indices = [Index(value = ["tagId"]), Index(value = ["postId"])]
)
data class SalesPostTagCrossRef(
    val postId: Int,
    val tagId: Int
)
