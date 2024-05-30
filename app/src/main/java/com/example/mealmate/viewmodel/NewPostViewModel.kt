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
   private suspend fun uploadImage():Uri? {

       return if(imageSelected.value != null){
            try {
                val recipeImagesRef = Firebase.storage.reference.child("recipesPosts/${recipePostId.value}.jpg")
                recipeImagesRef.putFile(imageSelected.value!!).await()
                uiState.postValue(UIState.WAITING)
                recipeImagesRef.downloadUrl.await()
            }catch (e:Exception){
                e.printStackTrace()
                null
            }
        }else{
            null
        }

    }

    fun uploadPost(newPost: RecipePost) {

        uiState.postValue(UIState.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postImageUrl = uploadImage()
                if(postImageUrl != null){
                    newPost.copy(postImageURL = postImageUrl.toString()).also {
                        Firebase.firestore.collection("recipePosts").document(it.id).set(it)
                            .await()
                        uiState.postValue(UIState.SUCCESS)
                    }
                }else{
                    uiState.postValue(UIState.ERROR)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                uiState.postValue(UIState.ERROR)
            }
        }
    }

    private fun getUsername(){
        uiState.postValue(UIState.LOADING)
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
                uiState.postValue(UIState.ERROR)
                e.printStackTrace()
            }finally {
                uiState.postValue(UIState.WAITING)
            }
        }
    }

    fun init(){
        getUsername()
    }
}