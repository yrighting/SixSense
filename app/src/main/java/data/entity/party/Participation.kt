package data.entity.party

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Participation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val postId: Int,
    val userId: String
)
