package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.repository.RecipeRepository
import com.example.mealmate.repository.RecipeRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel(
    private val recipesRepo:RecipeRepository = RecipeRepositoryImpl()
):ViewModel() {

    val recipeList  = MutableLiveData(emptyList<Recipe>())
    val isLoading  = MutableLiveData(false)

    fun refresh(){
        isLoading.postValue(true)
        viewModelScope.launch (Dispatchers.IO){
            val recipes = recipesRepo.getAllRecipes()
            if(recipes != null){
                recipeList.postValue(recipes)
            }else{
                recipeList.postValue(emptyList())
            }
               isLoading.postValue(false)
        }
    }
}