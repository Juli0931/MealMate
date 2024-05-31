package com.example.mealmate.domain.model

import com.google.type.DateTime

data class RecipePost(
    val id:String = "",
    val username:String = "",
    val profileImageURL:String = "",
    val postImageURL:String = "",
    val timestamp:Long = 0L,
    val description:String = "",
    val totalLikes:Int = 0,
    val totalComments:Int = 0,
    val totalShares:Int = 0,
    val comments:List<Comment> = emptyList(),
    val likes: List<String> = emptyList()
    )
