package com.example.mealmate.repository

import com.example.mealmate.domain.model.UserPreference
import com.example.mealmate.services.SurveyServices

interface SurveyRepository {
    suspend fun getPreferencesByField(field: String): List<UserPreference>
}

class SurveyRepositoryImpl(
    private val surveyServices: SurveyServices = SurveyServices(),
) : SurveyRepository {

    override suspend fun getPreferencesByField(field: String): List<UserPreference> =
        surveyServices.getPreferencesByField(field)

}