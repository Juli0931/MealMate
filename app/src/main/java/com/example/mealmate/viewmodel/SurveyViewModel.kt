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

class SurveyViewModel(private val userRepo: UserRepository = UserRepositoryImpl()) : ViewModel() {

    //Estado
    val surveyState = MutableLiveData(SurveyState.WAITING)

    val diets = MutableLiveData(mutableListOf<String>())

    //Los eventos de entrada
    fun uploadSurvey() {
        viewModelScope.launch(Dispatchers.IO) {
            surveyState.postValue(SurveyState.LOADING)
            val surveyUploaded = userRepo.uploadDiets(diets.value)
            if(surveyUploaded){
                surveyState.postValue(SurveyState.SUCCESS)
            }else{
                surveyState.postValue(SurveyState.ERROR)
            }
        }
    }
}