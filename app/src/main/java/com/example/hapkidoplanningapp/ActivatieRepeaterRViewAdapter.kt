package com.example.hapkidoplanningapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hapkidoplanningapp.domain.RepeatingActivates
import java.text.SimpleDateFormat
import java.util.Locale

class ActivatieRepeaterRViewAdapter(
    private val repeatingActivatiesList: List<RepeatingActivates>
): RecyclerView.Adapter<ActivatieRepeaterRViewAdapter.ViewHolderClass>() {

    inner class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvTitle: TextView = itemView.findViewById(R.id.titlefield)
        val rvPlace: TextView = itemView.findViewById(R.id.placeField)
        val rvDescription: TextView = itemView.findViewById(R.id.descriptionField)
        val rvTime: TextView = itemView.findViewById(R.id.time)
        val rvTimeRepeatingField: TextView = itemView.findViewById(R.id.TimeRepeatingField)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activatie_respeating_r_view, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return repeatingActivatiesList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = repeatingActivatiesList[position]

        // Format the date object to display hours and minutes
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = dateFormat.format(currentItem.Time)

        holder.rvTime.text = formattedTime
        holder.rvPlace.text = currentItem.place
        holder.rvDescription.text = currentItem.description
        holder.rvTimeRepeatingField.text = currentItem.TimeRepeating
        holder.rvTitle.text = currentItem.title
    }
}