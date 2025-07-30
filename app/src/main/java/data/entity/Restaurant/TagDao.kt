package com.sixsense.app.data.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TagDao {

    @Query("SELECT * FROM tags")
    suspend fun getAllTags(): List<Tag>

    @Insert
    suspend fun insertTag(tag: Tag)

    @Insert
    suspend fun insertTags(tags: List<Tag>)
}
