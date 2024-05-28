package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Recipe
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel:ViewModel() {

    val recipeList  = MutableLiveData(emptyList<Recipe>())
    val isLoading  = MutableLiveData(false)


    fun refresh(){
        isLoading.postValue(true)
        viewModelScope.launch (Dispatchers.IO){
           try {
               val result = Firebase.firestore.collection("recipes").get().await()
               val list = mutableListOf<Recipe>()
               for (document in result){
                   list.add(document.toObject(Recipe::class.java))
               }
               recipeList.postValue(list)
           }catch (e:Exception){
               e.printStackTrace()
           }finally {
               isLoading.postValue(false)
           }
        }

    }

}