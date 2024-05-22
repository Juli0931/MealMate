package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IngredientsSelectorViewModel:ViewModel() {

    val ingredients = MutableLiveData(
        listOf(
            "Cebolla",
            "Tomate",
            "Huevos",
            "Sal",
            "Azucar"
        )
    )
}