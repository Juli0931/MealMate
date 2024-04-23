package com.example.mealmate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.User
import com.example.mealmate.repository.UserRepository
import com.example.mealmate.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(val userRepo: UserRepository = UserRepositoryImpl()) : ViewModel() {

    //Estado
    val userState = MutableLiveData<User>()

    //Los eventos de entrada
    fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepo.loadUser()
            user?.let {
                withContext(Dispatchers.Main) {
                    userState.value = it
                }
            }
        }
    }

    fun observeUser() {
        userRepo.observeUser {
            userState.value = it
        } //alfa(fun: (String) -> Unit)
    }
}