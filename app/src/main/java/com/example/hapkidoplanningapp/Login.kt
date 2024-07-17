package com.example.hapkidoplanningapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hapkidoplanningapp.domain.User
import com.example.hapkidoplanningapp.service.loginService
import com.example.hapkidoplanningapp.service.userService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Login : Fragment() {
    private lateinit var auth: FirebaseAuth

    private lateinit var password: EditText
    private lateinit var email: EditText

    private lateinit var loginButton: Button
    private  var currentUser: User? = null


    private lateinit var LS: loginService
    private lateinit var uS: userService

    private lateinit var uP: UserProvider

    private var navbarProvider: NavbarProvider? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        uS = userService();

        if (auth.currentUser != null) {
            CoroutineScope(Dispatchers.Main).launch {
                var user = auth.currentUser

                if (user != null) {
                    currentUser = uS.getUserByUID(user.uid)

                    uP.updateUser(currentUser)
                } else {
                    navbarProvider?.getBottomNav()?.visibility = View.GONE



                    password = view.findViewById(R.id.PasswordField)
                    email = view.findViewById(R.id.emailField)

                    loginButton = view.findViewById(R.id.loginButton)

                    LS = loginService();



                    loginButton.setOnClickListener {
                        login()
                    }

//          resterButton.setOnClickListener{
//            register()
//
                }
            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavbarProvider) {
            navbarProvider = context
        }
        if (context is UserProvider) {
            uP = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
//    private fun register() {
//        val emailText = email.text.toString()
//        val passwordText = password.text.toString()
//
//        if (emailText.isEmpty() || passwordText.isEmpty()) {
//            Toast.makeText(requireContext(), "Vul e-mail en wachtwoord in", Toast.LENGTH_SHORT).show()
//            return
//        }
//        val newUser = User(
//            name = emailText,
//            password = passwordText
//        )
//        val error = LS.regester(newUser)
//       if (error.isEmpty()){
//           Toast.makeText(requireContext(), "resister successful ", Toast.LENGTH_SHORT).show()
//
//       }
//        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
//
//    }
    fun login() {

        val emailText = email.text.toString()
        val passwordText = password.text.toString()



        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(requireContext(), "Vul e-mail en wachtwoord in", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            val error = LS.login(emailText, passwordText)

            if (error.isEmpty()) {
                Toast.makeText(requireContext(), "Login successful ${auth.currentUser?.email}", Toast.LENGTH_SHORT).show()
                navbarProvider?.getBottomNav()?.visibility = View.VISIBLE

                currentUser = uS.getUserByUID(auth.currentUser!!.uid)
                uP.updateUser(currentUser)
            } else {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }



    }


    companion object {

        @JvmStatic
        fun newInstance() = Login()
    }


}