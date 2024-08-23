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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hapkidoplanningapp.domain.User
import com.example.hapkidoplanningapp.service.userService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class trainerUserFragment : Fragment() {

    private  var selectedUser: User? = null
    private lateinit var uP: UserProvider
    private lateinit var navbarProvider: NavbarProvider
    private lateinit var rVU: RecyclerView
    private lateinit var uS: userService

    private lateinit var nameField: EditText
    private lateinit var isTrainerField: CheckBox
    private lateinit var beltSpinner: Spinner
    private lateinit var editButton: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trainer_user_f, container, false)

        navbarProvider.getBottomNav().visibility = View.GONE

        rVU = view.findViewById(R.id.recyclerViewUsers)
        nameField = view.findViewById(R.id.nameField)
        isTrainerField = view.findViewById(R.id.emailField)
        beltSpinner = view.findViewById(R.id.spinnerBelt)
        setupBeltSpinner()
        uS = userService()

        editButton = view.findViewById(R.id.editButton)

        editButton.setOnClickListener{
            editStudent()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rVU.layoutManager = GridLayoutManager(context, 2)

        CoroutineScope(Dispatchers.Main).launch {
            fillRecyclerView()
        }
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

    private suspend fun fillRecyclerView() {
        val userList = uS.getAllUsers()

        if (userList?.isEmpty() == false) {
            val adapter = userRViewAdapter(userList) { user ->
                selectedUser = user
                nameField.setText(user.name)
                isTrainerField.setChecked(user.isTrainer)
                beltSpinner.setSelection(user.belt.ordinal, true)
            }
            rVU.adapter = adapter

            if (userList.isNotEmpty()) {
                val user = userList[0]
                selectedUser = user
                nameField.setText(user.name)
                isTrainerField.setChecked(user.isTrainer)
                beltSpinner.setSelection(user.belt.ordinal, true)
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("user_value", selectedUser)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            selectedUser = savedInstanceState.getParcelable("user_value")

            // Check if selectedUser is null before using it
            selectedUser?.let { user ->
                nameField.setText(user.name)
                isTrainerField.isChecked = user.isTrainer
                beltSpinner.setSelection(user.belt.ordinal, true)
            }
        }


    }
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

    private fun editStudent() {
        selectedUser?.name = nameField.text.toString()
        selectedUser?.isTrainer = isTrainerField.isChecked
        selectedUser?.belt = beltGrade.entries.toTypedArray()[beltSpinner.selectedItemPosition]

        CoroutineScope(Dispatchers.Main).launch {
            val edited = selectedUser?.let { uS.editUserByEmail(it) }
            if (edited == true) {
                Toast.makeText(requireContext(), "Successfully edited the user ${isTrainerField.isSelected}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to edit the user", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            trainerUserFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}