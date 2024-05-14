package com.example.hapkidoplanningapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hapkidoplanningapp.domain.User
import com.example.hapkidoplanningapp.service.loginService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth



class Login : Fragment() {
    private lateinit var auth: FirebaseAuth

    private lateinit var password: EditText
    private lateinit var email: EditText

    private lateinit var loginButton: Button
    private lateinit var resterButton: Button

    private lateinit var LS: loginService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        password = view.findViewById(R.id.PasswordField)
        email = view.findViewById(R.id.emailField)

        loginButton = view.findViewById(R.id.loginButton)
        resterButton = view.findViewById(R.id.regesterButton)

        LS = loginService();

        val currentUser = auth.currentUser
        if (currentUser != null) {

        }

        loginButton.setOnClickListener{
            login()
        }

        resterButton.setOnClickListener{
            register()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    private fun register() {
        val emailText = email.text.toString()
        val passwordText = password.text.toString()

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(requireContext(), "Vul e-mail en wachtwoord in", Toast.LENGTH_SHORT).show()
            return
        }
        val newUser = User(
            name = emailText,
            password = passwordText
        )
        val error = LS.regester(newUser)
       if (error.isEmpty()){
           Toast.makeText(requireContext(), "resister successful ", Toast.LENGTH_SHORT).show()

       }
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()

    }
    fun login() {
        val emailText = email.text.toString()
        val passwordText = password.text.toString()



        if (emailText.isEmpty() || passwordText.isEmpty()) {
            return
        }

        val error = LS.login(emailText, passwordText)

        if (error.isEmpty()){
            Toast.makeText(requireContext(), "login successful ", Toast.LENGTH_SHORT).show()

        }
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()


    }



    companion object {

    }
}