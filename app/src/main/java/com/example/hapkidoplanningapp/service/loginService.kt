package com.example.hapkidoplanningapp.service

import com.example.hapkidoplanningapp.domain.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class loginService {
    val auth = Firebase.auth
    var error = ""
    val uS = userService()
    suspend fun login(email: String, password: String): String {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            ""
        } catch (e: Exception) {
            e.localizedMessage ?: "Onbekende fout"
        }
    }
    fun logout(){
        auth.signOut()
    }
    suspend fun register(user: User, previus: User?): String {
        val previousUser = previus
        return try {
            auth.createUserWithEmailAndPassword(user.eMail, user.password).await()

            uS.addUser(auth.currentUser, user)

            if (previousUser != null) {
                val previousEmail = previousUser.eMail
                val previousPassword =  previousUser.password
                if (!previousEmail.isNullOrEmpty()) {
                    auth.signInWithEmailAndPassword(previousEmail, previousPassword).await()
                }
            }
            ""
        } catch (e: Exception) {
            e.localizedMessage ?: "Onbekende fout"
        }
    }
}
