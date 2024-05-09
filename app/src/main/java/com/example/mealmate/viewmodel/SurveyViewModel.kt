package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.UserPreference
import com.example.mealmate.repository.SurveyRepository
import com.example.mealmate.repository.SurveyRepositoryImpl
import com.example.mealmate.repository.UserRepository
import com.example.mealmate.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    CONDIMENTS,
    GRAINS,
    VEGETABLES,
    OBJECTIVES,
    ABOUT_YOU
}

class SurveyViewModel(
    private val userRepo: UserRepository = UserRepositoryImpl(),
    private val surveyRepo: SurveyRepository = SurveyRepositoryImpl()
) : ViewModel() {

    //status
    val surveyState = MutableLiveData(SurveyState.WAITING)

    val diets = MutableLiveData(mutableListOf<UserPreference>())
    val exceptions = MutableLiveData(mutableListOf<UserPreference>())
    val vegetables = MutableLiveData(mutableListOf<UserPreference>())
    val grains = MutableLiveData(mutableListOf<UserPreference>())
    val condiments = MutableLiveData(mutableListOf<UserPreference>())

    val objectives = MutableLiveData(mutableListOf<UserPreference>())

    val currentPreferenceField = MutableLiveData(PreferenceField.ABOUT_YOU)

    //Los eventos de entrada
    fun uploadUserPreference() {
        viewModelScope.launch(Dispatchers.IO) {
            surveyState.postValue(SurveyState.LOADING)
            val listPreferences: List<UserPreference> = when (currentPreferenceField.value) {
                PreferenceField.DIETS -> diets.value?.toList() ?: emptyList()
                PreferenceField.EXCEPTIONS -> exceptions.value?.toList() ?: emptyList()
                PreferenceField.OBJECTIVES -> objectives.value?.toList() ?: emptyList()
                PreferenceField.INGREDIENTS -> {
                    vegetables.value?.toList() ?: emptyList()
                    grains.value?.toList() ?: emptyList()
                    condiments.value?.toList() ?: emptyList()
                }

                else -> emptyList()
            }
            val surveyUploaded = userRepo.uploadUserPreference(currentPreferenceField.value!!.name, listPreferences)
            if (surveyUploaded) {
                surveyState.postValue(SurveyState.SUCCESS)
            } else {
                surveyState.postValue(SurveyState.ERROR)
            }
        }
    }

    //TODO:refactorizar el viewmodel para el ultimo fragmento
    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            surveyState.postValue(SurveyState.LOADING)

            diets.postValue(userRepo.getUserPreference(PreferenceField.DIETS.name).toMutableList())
            exceptions.postValue(userRepo.getUserPreference(PreferenceField.EXCEPTIONS.name).toMutableList())
            objectives.postValue(userRepo.getUserPreference(PreferenceField.OBJECTIVES.name).toMutableList())
            vegetables.postValue(userRepo.getUserPreference(PreferenceField.VEGETABLES.name).toMutableList())
            grains.postValue(userRepo.getUserPreference(PreferenceField.GRAINS.name).toMutableList())
            condiments.postValue(userRepo.getUserPreference(PreferenceField.CONDIMENTS.name).toMutableList())


            surveyState.postValue(SurveyState.WAITING)
        }
    }


    fun getPreferencesByField(
        field: String,
        onSuccess: (result: List<UserPreference>) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = surveyRepo.getPreferencesByField(field)
            withContext(Dispatchers.Main) {
                onSuccess(result)
            }
        }
    }
}