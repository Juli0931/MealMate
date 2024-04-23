package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.AppAuthState
import com.example.mealmate.domain.model.User
import com.example.mealmate.repository.AuthRepository
import com.example.mealmate.repository.AuthRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(val repo: AuthRepository = AuthRepositoryImpl()) : ViewModel() {

    val authStatus = MutableLiveData<AppAuthState>()

    fun login(username: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                authStatus.value = AppAuthState.Loading("Cargando...")
            }
            val status = repo.login(username, pass) //10 s
            withContext(Dispatchers.Main) { authStatus.value = status }
        }

    }

}