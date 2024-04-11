package com.example.hapkidoplanningapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_add_activetie.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_add_activetie : Fragment() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDate: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var addButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the variables by finding corresponding views
        editTextTitle = view.findViewById(R.id.editTextText)
        editTextDate = view.findViewById(R.id.editTextDate)
        editTextDescription = view.findViewById(R.id.editTextTextMultiLine2)
        checkBox = view.findViewById(R.id.checkBox)
        addButton = view.findViewById(R.id.button2)

        // Set OnClickListener for the Button
        addButton.setOnClickListener {
            // Retrieve text from EditText fields
            val title = editTextTitle.text.toString()
            val date = editTextDate.text.toString()
            val description = editTextDescription.text.toString()

            // Retrieve the checked state of the CheckBox
            val isChecked = checkBox.isChecked



        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(false)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_activetie, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_add_activetie.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_add_activetie().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}