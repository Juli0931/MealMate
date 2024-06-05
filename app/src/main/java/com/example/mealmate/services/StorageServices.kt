package com.example.mealmate.services

import android.net.Uri
import com.example.mealmate.view.state.UIState
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

class StorageServices {

    suspend fun uploadImage(imageUriSelected:Uri?, recipePostId:String):Uri?{
        return if(imageUriSelected != null){
            try {
                val recipeImagesRef = Firebase.storage.reference.child("recipesPosts/${recipePostId}.jpg")
                recipeImagesRef.putFile(imageUriSelected).await()
                recipeImagesRef.downloadUrl.await()
            }catch (e:Exception){
                e.printStackTrace()
                null
            }
        }else{
            null
        }
    }
}