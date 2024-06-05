package com.example.mealmate.repository

import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.domain.model.UserPreference
import com.example.mealmate.services.RecipeServices
import com.example.mealmate.services.SurveyServices

interface RecipeRepository {
    suspend fun getAllRecipes(): List<Recipe>?
    suspend fun getRecipeById(recipeId:String):Recipe?

}

class RecipeRepositoryImpl(
    private val recipeServices: RecipeServices = RecipeServices(),
) : RecipeRepository {

    override suspend fun getAllRecipes(): List<Recipe>? =
        recipeServices.getAllRecipes()

    override suspend fun getRecipeById(recipeId: String): Recipe? =
        recipeServices.getRecipeById(recipeId)
}