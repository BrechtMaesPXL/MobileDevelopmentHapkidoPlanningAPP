package com.example.hapkidoplanningapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hapkidoplanningapp.domain.Activities
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class activatieRViewHolder( private val activities: List<Activities>): RecyclerView.Adapter<activatieRViewHolder.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activatie_r_view, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return  activities.size

    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = activities[position]
        holder.rvTitle.text = currentItem.title
        holder.rvDate.text = currentItem.dateActivities?.let { formatDate(it) }


    }
    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvTitle: TextView = itemView.findViewById(R.id.title)
        val rvDate: TextView = itemView.findViewById(R.id.date)

    }
    //TODO: find better place
    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd-MM-yy HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }
}