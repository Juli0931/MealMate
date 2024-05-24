package com.example.mealmate.domain.model

import com.google.type.DateTime

data class Comment (
    val id:String = "",
    val content:String = "",
    val username:String = "",
    val profileImageUrl:String = "",
    val timeComment:DateTime,
)
