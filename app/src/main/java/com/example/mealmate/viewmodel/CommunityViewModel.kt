package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.CurrentSession
import com.example.mealmate.domain.model.RecipePost
import com.example.mealmate.domain.model.User
import com.example.mealmate.repository.RecipePostRepository
import com.example.mealmate.repository.RecipePostRepositoryImpl
import com.example.mealmate.view.state.UIState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.type.DateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CommunityViewModel(
    private val recipePostRepository: RecipePostRepository = RecipePostRepositoryImpl()
): ViewModel() {

    val recipePostList = MutableLiveData<List<RecipePost>?>()
    val isLoading  = MutableLiveData(false)

    fun refresh(){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
                val recipePosts = recipePostRepository.getAllRecipePosts()
                if(recipePosts != null){
                    recipePostList.postValue(recipePosts)
                }else{
                    recipePostList.postValue(emptyList())
                }
                isLoading.postValue(false)
        }
    }

    fun updateLike(postId: String, selected: Boolean) {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
                recipePostRepository.updateLike(postId, selected)
                isLoading.postValue(false)
        }
    }

    fun sharePost(id: String) {

    }

    fun addComment(id: String) {

    }

}