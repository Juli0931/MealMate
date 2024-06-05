package com.example.mealmate.repository

import android.net.Uri
import com.example.mealmate.domain.model.Comment
import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.domain.model.RecipePost
import com.example.mealmate.domain.model.UserPreference
import com.example.mealmate.services.RecipePostServices
import com.example.mealmate.services.RecipeServices
import com.example.mealmate.services.SurveyServices

interface RecipePostRepository {
    suspend fun getAllRecipePosts(): List<RecipePost>?
    suspend fun updateLike(postId: String, selected: Boolean):Boolean
    suspend fun getCommentsByPostId(postID: String):List<Comment>?
    suspend fun uploadComment(comment:Comment, postID: String):Boolean
    suspend fun uploadRecipePost(postImageUrl: Uri?, newPost:RecipePost ): Boolean
}

class RecipePostRepositoryImpl(
    private val recipePostServices: RecipePostServices = RecipePostServices(),
) : RecipePostRepository {

    override suspend fun getAllRecipePosts(): List<RecipePost>? =
        recipePostServices.getAllRecipePosts()

    override suspend fun updateLike(postId: String, selected: Boolean):Boolean =
        recipePostServices.updateLike(postId, selected)

    override suspend fun getCommentsByPostId(postID: String): List<Comment>? =
        recipePostServices.getCommentsByPostId(postID)

    override suspend fun uploadComment(comment: Comment, postID: String): Boolean =
        recipePostServices.uploadComment(comment, postID)

    override suspend fun uploadRecipePost(postImageUrl: Uri?, newPost: RecipePost): Boolean =
        recipePostServices.uploadRecipePost(postImageUrl, newPost)
}