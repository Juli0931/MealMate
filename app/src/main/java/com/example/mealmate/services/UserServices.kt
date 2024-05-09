package com.example.mealmate.services

import com.example.mealmate.domain.model.User
import com.example.mealmate.domain.model.UserPreference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import com.google.firebase.functions.functions

class UserServices {


// CRUD - Create, Read, Update, Delete

    suspend fun createUser(user: User) {
        Firebase.firestore.collection("users").document(user.id).set(user).await()

    }

    suspend fun loadUser(id: String): DocumentSnapshot {
        val output = Firebase.firestore.collection("users").document(id).get().await()
        return output
    }

    suspend fun loadUserList(): QuerySnapshot {
        val output = Firebase.firestore.collection("users").get().await()
        return output
    }

    fun observeUser(id: String, callback: (DocumentSnapshot?) -> Unit) {
        Firebase.firestore.collection("users").document(id)
            .addSnapshotListener { snapshot, error ->
                callback(snapshot)
            }
    }

    suspend fun uploadUserPreference(field: String, userId: String, items: List<UserPreference>): Boolean {
        return try {
            val datosMap = HashMap<String, UserPreference>()
            deleteUserPreferenceList(field)

            for (item in items) {
                Firebase.firestore.collection("users").document(userId).collection(field)
                    .document(item.id.toString()).set(item).await()
            }



            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getUserPreference(field: String, userId: String): List<UserPreference> {

        val list: MutableList<UserPreference> = mutableListOf()

        return try {

            val result = Firebase.firestore.collection("users").document(userId).collection(field)
                .get().await()
            result.forEach {
                list.add(it.toObject(UserPreference::class.java))
            }
            list
        } catch (e: Exception) {
            e.printStackTrace()
            list
        }
    }

   private suspend fun deleteCollection(path: String):Boolean{

        return try {
            val token = FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()?.token
            val deleteFn = Firebase.functions.getHttpsCallable("recursiveDelete")
            withTimeout(10000) {
                deleteFn.call(hashMapOf("path" to path, "token" to token)).await()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteUserPreferenceList(preferenceField: String){
        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
        val pathUserPreference = Firebase.firestore.collection("users").document(email).collection(preferenceField).path
        deleteCollection(pathUserPreference)
    }


}