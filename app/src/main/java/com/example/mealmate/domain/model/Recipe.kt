package com.example.mealmate.domain.model

data class Recipe(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val kal: String = "",
    val weight: String = "",
    val img: String = "",
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList()
)