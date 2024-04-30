package com.example.hapkidoplanningapp.service

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.hapkidoplanningapp.domain.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class loginService {
    val auth = Firebase.auth
    var error = ""
     fun login(eMial: String, password: String): String {

        auth.signInWithEmailAndPassword(eMial, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                } else {
                    // Foutmelding weergeven als de login mislukt
                    error += task.exception?.localizedMessage
//                    Toast.makeText(requireContext(), "Login mislukt: $error", Toast.LENGTH_SHORT).show()
                }
            }
         return error
    }
    fun regester(user: User): String {
        auth.createUserWithEmailAndPassword(user.name, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                     error += task.exception?.localizedMessage
                }
            }
        return error

    }

}