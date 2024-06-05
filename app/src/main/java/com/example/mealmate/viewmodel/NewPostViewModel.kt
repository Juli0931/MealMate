package com.example.mealmate.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.RecipePost
import com.example.mealmate.domain.model.User
import com.example.mealmate.repository.RecipePostRepository
import com.example.mealmate.repository.RecipePostRepositoryImpl
import com.example.mealmate.repository.StorageRepository
import com.example.mealmate.repository.StorageRepositoryImpl
import com.example.mealmate.view.state.UIState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID


class NewPostViewModel(
    private val storageRepository: StorageRepository = StorageRepositoryImpl(),
    private val recipePostRepository: RecipePostRepository = RecipePostRepositoryImpl()
) : ViewModel() {

    val tempCameraImage = MutableLiveData<Uri>()
    val imageSelected = MutableLiveData<Uri>()
    val uiState = MutableLiveData(UIState.WAITING)
    val recipePostId = UUID.randomUUID().toString()

    fun uploadPost(newPost: RecipePost) {

        uiState.postValue(UIState.LOADING)
        viewModelScope.launch(Dispatchers.IO) {

            val postImageUrl = storageRepository.uploadImage(imageSelected.value, recipePostId)
            recipePostRepository.uploadRecipePost(postImageUrl, newPost).also { isSuccess ->
                if(isSuccess){
                    uiState.postValue(UIState.SUCCESS)
                }else{
                    uiState.postValue(UIState.ERROR)
                }
            }
        }
    }
}