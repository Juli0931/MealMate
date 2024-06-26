package com.example.mealmate.viewmodel

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.repository.RecipeRepository
import com.example.mealmate.repository.RecipeRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

enum class RecipeDetailMode {
    EDITION,
    VISUALIZATION
}
class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository = RecipeRepositoryImpl()
) : ViewModel() {

    val imageSelected = MutableLiveData<Uri?>()
    val tempCameraImage = MutableLiveData<Uri?>()
    val recipe = MutableLiveData<Recipe?>()
    val recipeId = MutableLiveData<String>()
    val ingredients = MutableLiveData<List<String>>(emptyList())
    val steps = MutableLiveData<List<String>>(emptyList())
    val mode = MutableLiveData<RecipeDetailMode>(RecipeDetailMode.VISUALIZATION)


    private val storageRef = Firebase.storage.reference

    //TODO: refactor method according to clean architecture
    fun uploadImage() {
        try {
            imageSelected.value?.let {
                viewModelScope.launch(Dispatchers.IO) {
                    val recipeImagesRef = storageRef.child("recipes/${recipeId.value}.jpg")
                    recipeImagesRef.putFile(it).await()
                    Log.d("SUCCESS", "Image")
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
     fun getRecipeById(recipeId:String){
            viewModelScope.launch(Dispatchers.IO) {
               val newRecipe =  recipeRepository.getRecipeById(recipeId)
                recipe.postValue(newRecipe)
                ingredients.postValue(newRecipe?.ingredients)
                steps.postValue(newRecipe?.steps)
                if (newRecipe != null) {
                    imageSelected.postValue(newRecipe.img.toUri())
                }
            }
    }

    private fun DocumentSnapshot.toRecipe(): Recipe {
        val description = getString("description") ?: ""
        val id = getString("id") ?: ""
        val img = getString("img") ?: ""
        val ingredients = get("ingredients") as? List<String> ?: emptyList()
        val kal = getString("kal") ?: ""
        val portion = getString("portion") ?: ""
        val steps = get("steps") as? List<String> ?: emptyList()
        val time = getString("time") ?: ""
        val title = getString("title") ?: ""
        val weight = getString("weight") ?: ""
        return Recipe(id, title, description, kal, weight, time, portion, img, ingredients, steps)
    }

    fun addIngredient(ingredient: String) {
        ingredients.postValue(ingredients.value?.plus(ingredient))
    }

    fun addStep(newStep: String) {
        steps.postValue(steps.value?.plus(newStep))
    }

    fun tryToEdit() {
        mode.postValue(RecipeDetailMode.EDITION)
    }

    fun uploadRecipe() {
        mode.postValue(RecipeDetailMode.VISUALIZATION)
    }
}