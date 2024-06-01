package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.CurrentSession
import com.example.mealmate.domain.model.RecipePost
import com.example.mealmate.domain.model.User
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

class CommunityViewModel : ViewModel() {

    val recipePostList = MutableLiveData<List<RecipePost>>()
    val isLoading  = MutableLiveData(false)

    fun refresh(){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val newPostList = mutableListOf<RecipePost>()
                val result = Firebase.firestore.collection("recipePosts")
                    .orderBy("timestamp", Query.Direction.DESCENDING).get().await()
                for (document in result){
                    newPostList.add(document.toObject(RecipePost::class.java))
                }
                recipePostList.postValue(newPostList)
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                isLoading.postValue(false)
            }
        }
    }

    fun updateLike(postId: String, selected: Boolean) {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                CurrentSession.currentUser.let {user ->
                    val result = Firebase.firestore.collection("recipePosts").document(postId).get().await()
                    val post = result.toObject(RecipePost::class.java)!!
                    val updatedLikes:List<String> = if (selected){
                       val set = post.likes.toMutableSet()
                        set.add(user.id)
                        set.toList()
                    }else{
                       val newList = post.likes.toMutableList()
                        newList.remove(user.id)
                        newList.toList()
                    }
                    val updatedPost = post.copy(
                        likes = updatedLikes,
                        totalLikes = updatedLikes.size
                    )
                    Firebase.firestore.collection("recipePosts").document(postId).set(updatedPost).await()
                }


            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                isLoading.postValue(false)
            }
        }
    }

    fun sharePost(id: String) {

    }

    fun addComment(id: String) {

    }

}