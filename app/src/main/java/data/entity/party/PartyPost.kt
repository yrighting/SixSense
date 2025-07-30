package data.entity.party

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "party_post")
data class PartyPost(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val content: String,
    val category: String,
    val location: String,
    val date: String,
    val time: String,
    val maxPeople: Int,
    val currentPeople: Int = 1,
    val author: String,
    val capacity: Int
)
