package com.example.mealmate.domain.model

data class Comment(
    val id:String = "",
    val username:String = "",
    val profileImageURL:String = "",
    val timestamp:Long = 0L,
    val comment:String = ""
)