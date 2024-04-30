package com.example.hapkidoplanningapp.service

import android.content.ContentValues.TAG
import android.util.Log
import com.example.hapkidoplanningapp.domain.Activities
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import java.util.Date

class activatiesService {

    val db = Firebase.firestore


    fun addActivaties( date: Date, title: String, description: String, place: String ) {
        val newActivities = Activities(
            dateActivities = date, // Assuming you want the current date
            title = title,
            description = description,
            place = place
        )
        Log.d(TAG, "" + newActivities)
        db.collection("Activaties")
            .add(newActivities)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


    }
    fun getActivaties(completion: (List<Activities>?, Exception?) -> Unit) {
        db.collection("Activaties")
            .orderBy("dateActivities", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val activitiesList = mutableListOf<Activities>()
                for (document in documents) {
                    val activities = document.toObject(Activities::class.java)
                    activitiesList.add(activities)
                }
                completion(activitiesList, null)
            }
            .addOnFailureListener { e ->
                completion(null, e)
                Log.w(TAG, "Error getting documents.", e)
            }
    }
}