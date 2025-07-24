package com.example.bungeapp

data class Party(
    val id: Int,
    val title: String,
    val time: String,
    val location: String,
    val description: String,
    val host: String,
    val chatRoomId: String
)