package com.sixsense.app.data.entity

import androidx.room.*

@Entity(tableName = "tags")
data class Tag(
    @PrimaryKey(autoGenerate = true) val tagId: Int = 0,
    val tagName: String
)