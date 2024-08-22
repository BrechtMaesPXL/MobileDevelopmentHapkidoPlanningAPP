package com.example.hapkidoplanningapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hapkidoplanningapp.domain.User
import com.example.hapkidoplanningapp.service.loginService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Regestration : Fragment() {
    private lateinit var auth: FirebaseAuth

    private lateinit var password: EditText
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var isTrainer: CheckBox
    private lateinit var beltSpinner: Spinner


    private lateinit var lS: loginService
    private lateinit var uP: UserProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        beltSpinner = view.findViewById(R.id.beltSpinner)
        setupBeltSpinner()

        password = view.findViewById(R.id.PasswordField)
        name = view.findViewById(R.id.userNameField)
        email = view.findViewById(R.id.emailField)
        isTrainer = view.findViewById(R.id.isTrainerBox)

        lS = loginService()

        val resterButton = view.findViewById<Button>(R.id.regesterButton)

        resterButton.setOnClickListener{
            register()
        }
    }
    //TODO: mabby cahnge
    private fun setupBeltSpinner() {
        val beltGrades = beltGrade.entries.map { it.name }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            beltGrades
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        beltSpinner.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return inflater.inflate(R.layout.fragment_regestration, container, false)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name_value", name.text.toString())
        outState.putString("password_value", password.text.toString())
        outState.putString("email_value", email.text.toString())
        outState.putBoolean("isTrainer_value", isTrainer.isSelected)


    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {


        super.onViewStateRestored(savedInstanceState)
        if(savedInstanceState != null){
            name.setText(savedInstanceState.getString("name_value"))
            password.setText(savedInstanceState.getString("password_value"))
            email.setText(savedInstanceState.getString("email_value"))
            isTrainer.setChecked(savedInstanceState.getBoolean("isTrainer_value"))

        }


    }
    private  fun register() {
        val emailText = name.text.toString()
        val passwordText = password.text.toString()
        val emailstext = email.text.toString()
        val istrainer = isTrainer.isChecked
        val belt = getSelectedBelt()

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(requireContext(), "Vul e-mail en wachtwoord in", Toast.LENGTH_SHORT).show()
            return
        }
        val newUser = User(
            name = emailText,
            password = passwordText,
            eMail = emailstext,
            belt = belt,
            isTrainer = istrainer

        )
        CoroutineScope(Dispatchers.Main).launch {
            val error = lS.register(newUser, uP.getUser())

            if (error.isEmpty()) {
                Toast.makeText(requireContext(), "resister successful ", Toast.LENGTH_LONG).show()
                emptyFields()
            }
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }
    private fun getSelectedBelt(): beltGrade {
        val selectedBeltName = beltSpinner.selectedItem as String
        return beltGrade.valueOf(selectedBeltName)
    }
    private fun emptyFields() {
        name.text.clear()
        email.text.clear()
        password.text.clear()
        isTrainer.isChecked = false
        beltSpinner.setSelection(0)
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            Regestration().apply {
                arguments = Bundle().apply {

                }
            }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is NavbarProvider) {
//            navbarProvider = context
//        }
        if (context is UserProvider) {
            uP = context
        }
    }
}