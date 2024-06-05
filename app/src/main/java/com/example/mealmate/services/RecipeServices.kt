package com.example.mealmate.services

import com.example.mealmate.domain.model.Recipe
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class RecipeServices {

    suspend fun getAllRecipes():List<Recipe>?{
        return try {
            val result = Firebase.firestore.collection("recipes").get().await()
            val list = mutableListOf<Recipe>()
            for (document in result){
                list.add(document.toObject(Recipe::class.java))
            }
            list
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
    suspend fun getRecipeById(recipeId:String):Recipe?{
        return try{
            val result = Firebase.firestore.collection("recipes").document(recipeId).get().await()
            result.toObject(Recipe::class.java)

        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
}