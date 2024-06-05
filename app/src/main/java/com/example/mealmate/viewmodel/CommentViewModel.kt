package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Comment
import com.example.mealmate.domain.model.RecipePost
import com.example.mealmate.repository.RecipePostRepository
import com.example.mealmate.repository.RecipePostRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CommentViewModel(
    private val recipePostRepository: RecipePostRepository = RecipePostRepositoryImpl()
):ViewModel() {

    val comments = MutableLiveData<List<Comment>?>()
    lateinit var postID:String

    fun refresh(){
        viewModelScope.launch(Dispatchers.IO) {
            val newComments = recipePostRepository.getCommentsByPostId(postID)
            if(newComments != null){
                comments.postValue(newComments)
            }else{
                comments.postValue(emptyList())
            }
        }
    }
    fun uploadComment(comment: Comment){
        viewModelScope.launch(Dispatchers.IO) {
           recipePostRepository.uploadComment(comment, postID)
            refresh()
        }
    }
}