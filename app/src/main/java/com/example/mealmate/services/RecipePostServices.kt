package com.example.mealmate.services

import android.net.Uri
import androidx.core.net.toUri
import com.example.mealmate.domain.model.Comment
import com.example.mealmate.domain.model.CurrentSession
import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.domain.model.RecipePost
import com.example.mealmate.view.state.UIState
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class RecipePostServices {

    suspend fun getAllRecipePosts():List<RecipePost>?{
        return try{
            val newPostList = mutableListOf<RecipePost>()
            val result = Firebase.firestore.collection("recipePosts")
                .orderBy("timestamp", Query.Direction.DESCENDING).get().await()
            for (document in result){
                newPostList.add(document.toObject(RecipePost::class.java))
            }
           newPostList
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun updateLike(postId: String, selected: Boolean):Boolean{
        return try {
            val user = CurrentSession.currentUser
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
            true
        }catch (e:Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun getCommentsByPostId(postID: String):List<Comment>?{
       return try {
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
            newComments
        }catch (e:Exception){
            e.printStackTrace()
           null
        }
    }

    suspend fun uploadComment(comment:Comment, postID: String):Boolean{
        return try {
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
            true
        }catch (e:Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun uploadRecipePost(postImageUrl:Uri?,newPost:RecipePost ): Boolean{
       return try {
            if(postImageUrl != null){
                newPost.copy(postImageURL = postImageUrl.toString()).also {
                    Firebase.firestore.collection("recipePosts").document(it.id).set(it)
                        .await()
                }
                true
            }else{
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
           false
        }
    }
}