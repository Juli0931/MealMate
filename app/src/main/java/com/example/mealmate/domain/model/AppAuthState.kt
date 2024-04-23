package com.example.mealmate.domain.model

sealed class AppAuthState {
    //Estado 1
    data class Loading(val message: String) : AppAuthState()
    //Estado 2
    data class Error(val message: String) : AppAuthState()
    //Estado 3
    data class Success(val userID: String) : AppAuthState()

}