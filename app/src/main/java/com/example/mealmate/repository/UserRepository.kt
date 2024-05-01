package com.example.mealmate.repository

import com.example.mealmate.domain.model.User
import com.example.mealmate.services.UserServices
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

interface UserRepository {
    suspend fun loadUser(): User?
    fun observeUser(callback: (User) -> Unit)
    suspend fun uploadUserPreference(field:String, diets: List<String>?): Boolean
}

class UserRepositoryImpl(
    val userServices: UserServices = UserServices()
) : UserRepository {

    override suspend fun loadUser(): User? {
        val document = userServices.loadUser(Firebase.auth.uid!!)
        //Document -> Obj
        val user = document.toObject(User::class.java)
        return user
    }

    override fun observeUser(callback: (User) -> Unit) {
        userServices.observeUser(Firebase.auth.uid!!) { snapshot ->
            val user = snapshot?.toObject(User::class.java)
            user?.let {
                callback(it)
            }
        }
    }

    override suspend fun uploadUserPreference(field:String, diets: List<String>?): Boolean =
        userServices.uploadUserPreference(field, Firebase.auth.uid!!, diets ?: listOf(""))

}
