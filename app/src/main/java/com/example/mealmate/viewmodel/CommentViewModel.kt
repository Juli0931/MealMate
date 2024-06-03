package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Comment
import com.example.mealmate.domain.model.RecipePost
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CommentViewModel:ViewModel() {

    val comments = MutableLiveData<List<Comment>>()
    lateinit var postID:String

    fun refresh(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newComments = mutableListOf<Comment>()
               val result = Firebase.firestore
                   .collection("recipePosts")
                   .document(postID)
                   .collection("comments")
                   .orderBy("timestamp",Query.Direction.DESCENDING)
                   .get().await()
                result.forEach { document ->
                    document.toObject(Comment::class.java).also {
                        newComments.add(it)
                    }
                }
                comments.postValue(newComments)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    fun uploadComment(comment: Comment){
        viewModelScope.launch(Dispatchers.IO) {
            try {
             Firebase.firestore
                 .collection("recipePosts")
                 .document(postID)
                 .collection("comments")
                 .add(comment)
                 .await()

             val result = Firebase.firestore
                    .collection("recipePosts")
                    .document(postID)
                    .get()
                    .await()

                val recipePost = result.toObject(RecipePost::class.java)!!
                val updatedPost = recipePost.copy(
                    totalComments = recipePost.totalComments + 1
                )

                 Firebase.firestore
                    .collection("recipePosts")
                    .document(postID)
                    .set(updatedPost)
                    .await()

                refresh()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }


    }
}