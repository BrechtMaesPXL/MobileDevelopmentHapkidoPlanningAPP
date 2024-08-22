package com.example.hapkidoplanningapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hapkidoplanningapp.domain.Activities
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailActivatie : Fragment() {

    private var activities: Activities? = null

    companion object {
        // Key for the argument
        private const val ARG_ACTIVITIES = "activities"

        // Factory method to create a new instance of DetailActivatie fragment
        fun newInstance(activities: Activities): DetailActivatie {
            val fragment = DetailActivatie()
            val args = Bundle()
            // Pass the Activities object as an argument
            args.putSerializable(ARG_ACTIVITIES, activities)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the Activities object from the arguments
        arguments?.let {
            activities = it.getSerializable(ARG_ACTIVITIES) as Activities?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_activatie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the text of TextViews with the data from the Activities object
        activities?.let {
            view.findViewById<TextView>(R.id.title).text = it.title
            view.findViewById<TextView>(R.id.desciption).text = it.description
            view.findViewById<TextView>(R.id.Date).text = formatDate(it.dateActivities)
            view.findViewById<TextView>(R.id.place).text = it.place
            view.findViewById<TextView>(R.id.attendees).text = it.attendees?.count().toString()
            view.findViewById<TextView>(R.id.Trainer).text = "Trainer: ${it.trainer?.name},${it.trainer?.belt} "
        }
    }

    // Utility method to format date
    private fun formatDate(date: Date?): String {
        val dateFormat = SimpleDateFormat("dd-MM-yy HH:mm", Locale.getDefault())
        return date?.let { dateFormat.format(it) } ?: ""
    }
}
