package com.example.mealmate.domain.model

data class Recipe(
    val id:String = "",
    val title:String = "",
    val description:String = "",
    val kal:Int = 0,
    val weight:Int = 0,
    val img:String = "",
)

fun Int.toGramsFormat() =  "${this}g"
fun Int.toKalFormat() = "$this cal"

