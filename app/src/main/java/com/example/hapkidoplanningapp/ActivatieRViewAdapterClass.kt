package com.example.hapkidoplanningapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hapkidoplanningapp.domain.Activities
import com.example.hapkidoplanningapp.service.MyActivatiesDAO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class activatieRViewHolder(
    private val activities: MutableList<Activities>,
    private val dbLocal: dbLocal,
    private val isLocal: Boolean = false,
) : RecyclerView.Adapter<activatieRViewHolder.ViewHolderClass>() {

    private lateinit var myActivatiesDAO: MyActivatiesDAO

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activatie_r_view, parent, false)

        myActivatiesDAO = dbLocal.getdb().MyActivatiesDAO()

        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return  activities.size

    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = activities[position]
        holder.rvTitle.text = currentItem.title
        holder.rvDate.text = currentItem.dateActivities?.let { formatDate(it) }
        if (isLocal) {
            holder.rvAddButton.setImageResource(R.drawable.baseline_delete_outline_24)
        } else {
            holder.rvAddButton.setImageResource(R.drawable.baseline_add_24)
        }
        holder.rvAddButton.setOnClickListener {
            insertActivityIntoDatabase(currentItem)

        }
    }
    fun updateData(newActivities: List<Activities>) {
        activities.clear()
        activities.addAll(newActivities)
        notifyDataSetChanged()
    }
    private fun insertActivityIntoDatabase(activity: Activities) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (isLocal){
                    dbLocal.getdb().MyActivatiesDAO().delete(activity)
                }else {
                    dbLocal.getdb().MyActivatiesDAO().insertAll(activity)
                }
                updateData(dbLocal.getdb().MyActivatiesDAO().getAll())

            } catch (e: Exception) {
                Log.e("InsertActivity", "Error inserting activity: ${e.message}")
            }
        }

    }
    class ViewHolderClass(itemView: View, ): RecyclerView.ViewHolder(itemView) {
        val rvTitle: TextView = itemView.findViewById(R.id.title)
        val rvDate: TextView = itemView.findViewById(R.id.date)
        val rvAddButton: FloatingActionButton = itemView.findViewById(R.id.addMyActivitieButton)
    }
    //TODO: find better place
    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd-MM-yy HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }
}