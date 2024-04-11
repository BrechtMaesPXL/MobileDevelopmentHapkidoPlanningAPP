package com.example.hapkidoplanningapp.service

import android.content.ContentValues.TAG
import android.util.Log
import com.example.hapkidoplanningapp.domain.Activities
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Date

class activatiesService {

    val db = Firebase.firestore

    fun addActivaties(id: Int, date: Date, title: String, description: String, place: String ) {
        val newActivities = Activities(
            dateActivities = Date(), // Assuming you want the current date
            title = title,
            description = description,
            place = place
        )
        db.collection("Activaties")
            .add(newActivities)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


    }
}