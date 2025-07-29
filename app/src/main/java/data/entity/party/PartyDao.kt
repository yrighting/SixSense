package data.entity.party

import androidx.room.*

@Dao
interface PartyDao {

    @Insert
    suspend fun insertPartyPost(partyPost: PartyPost)

    @Query("SELECT * FROM party_post ORDER BY id DESC")
    suspend fun getAllPartyPosts(): List<PartyPost>

    @Query("SELECT * FROM party_post WHERE id = :postId")
    suspend fun getPartyPostById(postId: Int): PartyPost

    @Update
    suspend fun updatePartyPost(partyPost: PartyPost)

    @Insert
    suspend fun insertComment(comment: PartyComment)

    @Query("SELECT * FROM party_comment WHERE postId = :postId ORDER BY id ASC")
    suspend fun getCommentsForPost(postId: Int): List<PartyComment>

    @Insert
    suspend fun insertParticipation(participation: Participation)

    @Query("SELECT EXISTS(SELECT 1 FROM Participation WHERE postId = :postId AND userId = :userId)")
    suspend fun isUserAlreadyJoined(postId: Int, userId: String): Boolean

    @Query("SELECT party_post.* FROM party_post INNER JOIN participation ON party_post.id = participation.postId WHERE participation.userId = :userId")
    suspend fun getMyParticipatedPosts(userId: String): List<PartyPost>
}
