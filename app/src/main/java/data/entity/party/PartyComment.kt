package data.entity.party

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "party_comment")
data class PartyComment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val postId: Int,
    val author: String,
    val content: String,
    val timestamp: String
)
