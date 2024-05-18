package com.example.mealmate.viewmodel

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Recipe
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RecipeDetailViewModel : ViewModel() {

    val imageSelected = MutableLiveData<Uri?>()
    val tempCameraImage = MutableLiveData<Uri?>()
    val recipe = MutableLiveData<Recipe?>()
    val recipeId = MutableLiveData<String>()

    val storageRef = Firebase.storage.reference

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
     fun downloadRecipe(recipeId:String){
            viewModelScope.launch(Dispatchers.IO) {
                try{
                    val result = Firebase.firestore.collection("recipes").document(recipeId).get().await()
                    val newRecipe = result.toObject(Recipe::class.java)
                    recipe.postValue(newRecipe)
                    imageSelected.postValue(newRecipe?.img?.toUri())
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

    }
}