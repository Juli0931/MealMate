package com.example.mealmate.view.util

fun List<String>.hasUserLikedIt(userId:String): Boolean{
    val like = this.find { it == userId }
    return like != null
}