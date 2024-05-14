package com.example.mealmate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Recipe
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel:ViewModel() {

    val recipeList  = MutableLiveData(emptyList<Recipe>())

    fun init(){
        viewModelScope.launch (Dispatchers.IO){
            val result = Firebase.firestore.collection("recipes").get().await()
            val list = mutableListOf<Recipe>()
            for (document in result){
                list.add(document.toObject(Recipe::class.java))
            }
            recipeList.postValue(list)
        }

    }

}