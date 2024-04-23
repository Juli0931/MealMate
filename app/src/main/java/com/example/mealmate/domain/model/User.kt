package com.example.mealmate.domain.model

data class User (
    var id : String = "",
    var username: String = "",
    var email: String = "",
    var name: String = "",
    val diets: List<String> = emptyList()
    )