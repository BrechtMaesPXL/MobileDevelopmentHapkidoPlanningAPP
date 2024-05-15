package com.example.hapkidoplanningapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hapkidoplanningapp.domain.Activities
import com.example.hapkidoplanningapp.service.MyActivatiesDAO
import com.example.hapkidoplanningapp.service.activatiesService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class activatieRViewHolder(
    private val activities: MutableList<Activities>,
    private val dbLocal: dbLocal,
    private val isLocal: Boolean = false,
) : RecyclerView.Adapter<activatieRViewHolder.ViewHolderClass>() {

    private lateinit var myActivatiesDAO: MyActivatiesDAO
    private lateinit var aS: activatiesService


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activatie_r_view, parent, false)

        myActivatiesDAO = dbLocal.getdb().MyActivatiesDAO()
        aS = activatiesService()

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
            holder.rvDelButton.setVisibility(View.GONE);
            holder.rvAddButton.setImageResource(R.drawable.baseline_delete_outline_24)
        } else {
            holder.rvAddButton.setImageResource(R.drawable.baseline_add_24)
            holder.rvDelButton.setOnClickListener {
                delActivity(currentItem)
            }
        }
        holder.rvAddButton.setOnClickListener {
            insertActivityIntoDatabase(currentItem)
        }
    }
    private suspend fun updateUI() {
        withContext(Dispatchers.Main) {
            notifyDataSetChanged()
        }
    }
    private fun insertActivityIntoDatabase(activity: Activities) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (isLocal){
                    dbLocal.getdb().MyActivatiesDAO().delete(activity)
                } else {
                    dbLocal.getdb().MyActivatiesDAO().insertAll(activity)
                }
                // Update the UI on the main thread if needed
                updateUI()
            } catch (e: Exception) {
                Log.e("InsertActivity", "Error inserting activity: ${e.message}")
            }
        }
    }
    private fun delActivity(activity: Activities) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                aS.delActivaties(activity)
                updateUI()
            } catch (e: Exception) {
                Log.e("DeleteActivity", "Error deleting activity: ${e.message}")
            }
        }
    }


    class ViewHolderClass(itemView: View, ): RecyclerView.ViewHolder(itemView) {
        val rvTitle: TextView = itemView.findViewById(R.id.title)
        val rvDate: TextView = itemView.findViewById(R.id.date)
        val rvAddButton: FloatingActionButton = itemView.findViewById(R.id.addMyActivitieButton)
        val rvDelButton: FloatingActionButton = itemView.findViewById(R.id.delMyActivitieButton)
    }
    //TODO: find better place
    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd-MM-yy HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }

}