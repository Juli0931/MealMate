package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.RecipePost
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.type.DateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CommunityViewModel : ViewModel() {

    val recipePostList = MutableLiveData<List<RecipePost>>(dummyPost)

    fun refresh(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val newPostList = mutableListOf<RecipePost>()
                val result = Firebase.firestore.collection("recipePosts").get().await()
                for (document in result){
                    newPostList.add(document.toObject(RecipePost::class.java))
                }
                recipePostList.postValue(newPostList)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

    fun updateLike(id: String, selected: Boolean) {

    }

    fun sharePost(id: String) {

    }

    fun addComment(id: String) {

    }

}
val dummyPost = listOf<RecipePost>(
    RecipePost(
        id = "sagsgsdgasad",
        username = "domigod",
        profileImageURL = "",
        postImageURL = "",
        timestamp = System.currentTimeMillis(),
        description = "Alguien quiere papas",
        totalLikes = 0,
        totalComments = 0,
        totalShares = 0,
        comments = emptyList(),
    ),
    RecipePost(
        id = "sagsgsdgasad",
        username = "domigod",
        profileImageURL = "",
        postImageURL = "",
        timestamp = System.currentTimeMillis(),
        description = "Alguien quiere papas",
        totalLikes = 0,
        totalComments = 0,
        totalShares = 0,
        comments = emptyList(),
    ),
    RecipePost(
        id = "sagsgsdgasad",
        username = "domigod",
        profileImageURL = "",
        postImageURL = "",
        timestamp = System.currentTimeMillis(),
        description = "Alguien quiere papas",
        totalLikes = 0,
        totalComments = 0,
        totalShares = 0,
        comments = emptyList(),
    )
)