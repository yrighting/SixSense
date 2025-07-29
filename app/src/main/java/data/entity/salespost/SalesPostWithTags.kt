package data.entity.salespost

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.sixsense.app.data.entity.Tag

data class SalesPostWithTags(
    @Embedded val post: SalesPost,
    @Relation(
        parentColumn = "postId",
        entityColumn = "tagId",
        associateBy = Junction(SalesPostTagCrossRef::class)
    )
    val tags: List<Tag>
)

