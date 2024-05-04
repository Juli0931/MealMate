package com.example.mealmate.services

import com.example.mealmate.domain.model.UserPreference
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.tasks.await

class SurveyServices {

    suspend fun getPreferencesByField(field: String): List<UserPreference> {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        return try {
           firebaseRemoteConfig.fetchAndActivate().await()
            val json = firebaseRemoteConfig.getString(field)
            val listType = object : TypeToken<List<UserPreference>>() {}.type  //gpt
            Gson().fromJson(json, listType)
        } catch (e: Exception) {
            emptyList()
        }
    }
}