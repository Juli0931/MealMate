package com.example.mealmate.repository

import android.net.Uri
import com.example.mealmate.domain.model.UserPreference
import com.example.mealmate.services.StorageServices
import com.example.mealmate.services.SurveyServices
import io.grpc.Context.Storage

interface StorageRepository {
    suspend fun uploadImage(imageUriSelected: Uri?, recipePostId:String): Uri?
}

class StorageRepositoryImpl(
    private val storageServices: StorageServices = StorageServices(),
) : StorageRepository {

    override suspend fun uploadImage(imageUriSelected:Uri?, recipePostId:String):Uri? =
        storageServices.uploadImage(imageUriSelected, recipePostId)
}