package com.example.mealmate.repository

import com.example.mealmate.domain.model.AppAuthState
import com.example.mealmate.domain.model.User
import com.example.mealmate.services.AuthServices
import com.example.mealmate.services.UserServices
import com.google.firebase.auth.FirebaseAuthException

interface AuthRepository {
    suspend fun login(email: String, pass: String): AppAuthState
    suspend fun signup(user: User, pass: String): AppAuthState
}

class AuthRepositoryImpl(
    val authServices: AuthServices = AuthServices(),
    val userServices: UserServices = UserServices()
) : AuthRepository {
    override suspend fun login(email: String, pass: String): AppAuthState {
        try {
            val result = authServices.login(email, pass)
            result.user?.let {
                return AppAuthState.Success("")
            } ?: run {
                return AppAuthState.Error("")
            }
        } catch (ex: FirebaseAuthException) {
            return AppAuthState.Error("")
        }
    }

    override suspend fun signup(user: User, pass: String): AppAuthState {
        try {
            //1. Registro
            val result = authServices.signup(user.email, pass)
            result.user?.let {
                //2. Guardar usuario en base de datos
                user.id = it.uid
                userServices.createUser(user)
                return AppAuthState.Success(it.uid)
            } ?: run {
                return AppAuthState.Error("Something went wrong")
            }
        } catch (ex: FirebaseAuthException) {
            return AppAuthState.Error(ex.errorCode)
        }
    }
}