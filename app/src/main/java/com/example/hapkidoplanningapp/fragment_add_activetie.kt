package com.example.hapkidoplanningapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.hapkidoplanningapp.service.activatiesService
import java.util.Calendar


class fragment_add_activetie : Fragment() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextPlace: EditText
    private lateinit var addButton: Button
    private lateinit var calendarView: CalendarView
    private lateinit var aS: activatiesService
    private lateinit var uP: UserProvider
    private lateinit var timePicker: TimePicker

    private var navbarProvider: NavbarProvider? = null
    private var selectedDate: Long = Calendar.getInstance().timeInMillis // Initialize with the current date


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextTitle = view.findViewById(R.id.editTextText)
        editTextDescription = view.findViewById(R.id.editTextTextMultiLine2)
        editTextPlace = view.findViewById(R.id.PlaceField)
        addButton = view.findViewById(R.id.button2)
        calendarView = view.findViewById(R.id.calendar)
        timePicker = view.findViewById(R.id.timePicker)
        timePicker.setIs24HourView(true)
        if (savedInstanceState != null) {
            editTextTitle.setText(savedInstanceState.getString("title_value"))
            editTextDescription.setText(savedInstanceState.getString("description_value"))
            editTextPlace.setText(savedInstanceState.getString("place_value"))

            timePicker.hour = savedInstanceState.getInt("timePicker_hour_value", 0)
            timePicker.minute = savedInstanceState.getInt("timePicker_minute_value", 0)
        }
        aS = activatiesService()

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        }

        navbarProvider?.getBottomNav()?.visibility = View.GONE

        addButton.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()
            val place = editTextPlace.text.toString()

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = calendarView.date
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)

            val selectedDate = calendar.time

            aS.addActivaties(selectedDate, title, uP.getUser(), description, place)

            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("title_value", editTextTitle.text.toString())
        outState.putString("description_value", editTextDescription.text.toString())
        outState.putString("place_value", editTextPlace.text.toString())
        outState.putLong("selected_date", selectedDate)
        outState.putInt("timePicker_hour_value", timePicker.hour)
        outState.putInt("timePicker_minute_value", timePicker.minute)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {


        super.onViewStateRestored(savedInstanceState)
        if(savedInstanceState != null){
            editTextTitle.setText(savedInstanceState.getString("title_value"))
            editTextDescription.setText(savedInstanceState.getString("description_value"))
            editTextPlace.setText(savedInstanceState.getString("place_value"))
            selectedDate = savedInstanceState.getLong("selected_date", Calendar.getInstance().timeInMillis)
            calendarView.date = selectedDate


            timePicker.post {
                timePicker.hour = savedInstanceState.getInt("timePicker_hour_value")
                timePicker.minute = savedInstanceState.getInt("timePicker_minute_value")
            }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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