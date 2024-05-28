package com.example.mealmate.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.RecipePost
import com.example.mealmate.domain.model.User
import com.example.mealmate.view.state.UIState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.net.URI
import java.util.UUID


class NewPostViewModel : ViewModel() {

    val tempCameraImage = MutableLiveData<Uri>()
    val imageSelected = MutableLiveData<Uri>()
    val uiState = MutableLiveData(UIState.WAITING)
    val currentUser = MutableLiveData<User>()
    val recipePostId = MutableLiveData(UUID.randomUUID().toString())
    fun uploadImage() {
        try {
            imageSelected.value?.let {
                viewModelScope.launch(Dispatchers.IO) {
                    val recipeImagesRef = Firebase.storage.reference.child("recipesPosts/${recipePostId.value}.jpg")
                    recipeImagesRef.putFile(it).await()
                    Log.d("SUCCESS", "Image")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun uploadPost(newPost: RecipePost) {
        uploadImage()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Firebase.firestore.collection("recipePosts").document(newPost.id).set(newPost)
                    .await()
                uiState.postValue(UIState.SUCCESS)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState.postValue(UIState.ERROR)
            }
        }
    }

    private fun getUsername(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val email = Firebase.auth.currentUser?.email.toString()
                val result =
                    Firebase.firestore.collection("users").whereEqualTo("email", email).limit(1)
                        .get().await()
                result.documents[0].toObject(User::class.java)?.also {
                    currentUser.postValue(it)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun init(){
        getUsername()
    }
}