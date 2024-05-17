package com.example.mealmate.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.view.util.ImageUtil
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class RecipeDetailViewModel : ViewModel() {

    val imageSelected = MutableLiveData<Drawable>()
    val recipe = MutableLiveData<Recipe>()
    val recipeId = MutableLiveData<String>()

    val storageRef = Firebase.storage.reference

    fun uploadImage() {
        val bitmap = ImageUtil().drawableToBitmap(imageSelected.value!!)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val recipeImagesRef = storageRef.child("recipes/${recipeId.value}.jpg")
                recipeImagesRef.putBytes(data).await()
                Log.d("SUCCESS", "Image")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}