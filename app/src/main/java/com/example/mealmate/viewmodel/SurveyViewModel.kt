package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.UserPreference
import com.example.mealmate.repository.SurveyRepository
import com.example.mealmate.repository.SurveyRepositoryImpl
import com.example.mealmate.repository.UserRepository
import com.example.mealmate.repository.UserRepositoryImpl
import com.example.mealmate.view.state.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    val uiState = MutableLiveData(UIState.WAITING)

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
            uiState.postValue(UIState.LOADING)
            var surveyUploaded = false
             when (currentPreferenceField.value) {
                PreferenceField.DIETS ->{
                    val listPreferences = diets.value?.toList() ?: emptyList()
                    surveyUploaded = userRepo.uploadUserPreference(currentPreferenceField.value!!.name, listPreferences)
                }
                PreferenceField.EXCEPTIONS ->{
                    val listPreferences = exceptions.value?.toList() ?: emptyList()
                    surveyUploaded = userRepo.uploadUserPreference(currentPreferenceField.value!!.name, listPreferences)
                }
                PreferenceField.OBJECTIVES ->{
                    val listPreferences =  objectives.value?.toList() ?: emptyList()
                    surveyUploaded = userRepo.uploadUserPreference(currentPreferenceField.value!!.name, listPreferences)
                }
                PreferenceField.INGREDIENTS -> {
                    var listPreferences =  vegetables.value?.toList() ?: emptyList()
                    surveyUploaded = userRepo.uploadUserPreference(PreferenceField.VEGETABLES.name, listPreferences)
                     listPreferences =  grains.value?.toList() ?: emptyList()
                    surveyUploaded = userRepo.uploadUserPreference(PreferenceField.GRAINS.name, listPreferences) && surveyUploaded
                     listPreferences =  condiments.value?.toList() ?: emptyList()
                    surveyUploaded = userRepo.uploadUserPreference(PreferenceField.CONDIMENTS.name, listPreferences) && surveyUploaded
                }

                else -> emptyList<UserPreference>()
            }
            if (surveyUploaded) {
                uiState.postValue(UIState.SUCCESS)
            } else {
                uiState.postValue(UIState.ERROR)
            }
        }
    }

    //TODO:refactorizar el viewmodel para el ultimo fragmento
    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.postValue(UIState.LOADING)

            diets.postValue(userRepo.getUserPreference(PreferenceField.DIETS.name).toMutableList())
            exceptions.postValue(userRepo.getUserPreference(PreferenceField.EXCEPTIONS.name).toMutableList())
            objectives.postValue(userRepo.getUserPreference(PreferenceField.OBJECTIVES.name).toMutableList())
            vegetables.postValue(userRepo.getUserPreference(PreferenceField.VEGETABLES.name).toMutableList())
            grains.postValue(userRepo.getUserPreference(PreferenceField.GRAINS.name).toMutableList())
            condiments.postValue(userRepo.getUserPreference(PreferenceField.CONDIMENTS.name).toMutableList())


            uiState.postValue(UIState.WAITING)
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