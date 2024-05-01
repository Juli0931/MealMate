package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.repository.UserRepository
import com.example.mealmate.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class SurveyState {
    WAITING,
    LOADING,
    SUCCESS,
    ERROR
}
enum class PreferenceField {
    DIETS,
    EXCEPTIONS,
    INGREDIENTS,
    OBJECTIVES
}

class SurveyViewModel(private val userRepo: UserRepository = UserRepositoryImpl()) : ViewModel() {

    //Estado
    val surveyState = MutableLiveData(SurveyState.WAITING)

    val diets = MutableLiveData(mutableListOf<String>())
    val exceptions = MutableLiveData(mutableListOf<String>())
    val ingredients = MutableLiveData(mutableListOf<String>())
    val objectives = MutableLiveData(mutableListOf<String>())

    //Los eventos de entrada
    fun uploadUserPreference(field: PreferenceField) {
        viewModelScope.launch(Dispatchers.IO) {
            surveyState.postValue(SurveyState.LOADING)
            val listPreferences:List<String> = when (field) {
                PreferenceField.DIETS -> diets.value?.toList() ?: emptyList()
                PreferenceField.EXCEPTIONS -> exceptions.value?.toList() ?: emptyList()
                PreferenceField.INGREDIENTS -> ingredients.value?.toList() ?: emptyList()
                PreferenceField.OBJECTIVES -> objectives.value?.toList() ?: emptyList()
            }


            val surveyUploaded = userRepo.uploadUserPreference(field.name, listPreferences)
            if (surveyUploaded) {
                surveyState.postValue(SurveyState.SUCCESS)
            } else {
                surveyState.postValue(SurveyState.ERROR)
            }
        }
    }
}