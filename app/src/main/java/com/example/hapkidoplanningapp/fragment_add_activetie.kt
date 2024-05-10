package com.example.hapkidoplanningapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import com.example.hapkidoplanningapp.service.activatiesService
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_add_activetie.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_add_activetie : Fragment() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextPlace: EditText

    //private lateinit var checkBox: CheckBox
    private lateinit var addButton: Button
    private lateinit var calendarView: CalendarView
    private lateinit var aS: activatiesService

    private var navbarProvider: NavbarProvider? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextTitle = view.findViewById(R.id.editTextText)
        editTextDescription = view.findViewById(R.id.editTextTextMultiLine2)
       // checkBox = view.findViewById(R.id.checkBox)
        editTextPlace = view.findViewById(R.id.PlaceField)
        addButton = view.findViewById(R.id.button2)
        calendarView = view.findViewById(R.id.calendar)


        aS = activatiesService()

        navbarProvider?.getBottomNav()?.visibility = View.GONE

        addButton.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()
            val place = editTextPlace.text.toString()

            //val isChecked = checkBox.isChecked

            val selectedDate = Date(calendarView.date)


            aS.addActivaties(selectedDate, title, description, place)


            activity?.supportFragmentManager?.popBackStack()


        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavbarProvider) {
            navbarProvider = context
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
        @JvmStatic
        fun newInstance() =
            fragment_add_activetie().apply {
                arguments = Bundle().apply {
                }
            }
    }

}