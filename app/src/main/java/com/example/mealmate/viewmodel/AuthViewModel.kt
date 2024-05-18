package com.example.mealmate.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.AppAuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel: ViewModel() {

    val authStatus = MutableLiveData<AppAuthState>()

    fun verifyCurrentFirebaseToken() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    val result = user.getIdToken(true).await()
                    if (result?.token != null) {
                        authStatus.postValue(AppAuthState.Success(""))
                    } else {
                        Log.e("LoginViewModel", "TOKEN expired")
                    }
                } else {
                    Log.e("LoginViewModel", "No user is signed in.")
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Failed to retrieve token: ${e.message}")
            }
        }

    }
}