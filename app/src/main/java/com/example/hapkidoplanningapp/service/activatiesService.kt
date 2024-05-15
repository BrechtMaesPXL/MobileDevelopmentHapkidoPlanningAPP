package com.example.hapkidoplanningapp.service

import android.content.ContentValues.TAG
import android.util.Log
import com.example.hapkidoplanningapp.domain.Activities
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.util.Date

class activatiesService {

    private val db = Firebase.firestore
    private val activatieCollection = db.collection("Activaties")

    fun addActivaties( date: Date, title: String, description: String, place: String ) {
        val newActivities = Activities(
            dateActivities = date,
            title = title,
            description = description,
            place = place
        )

        activatieCollection
            .add(newActivities)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")

            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


    }
    suspend fun delActivaties(activaties: Activities) {
        val activatieQuery = activatieCollection
            .whereEqualTo("dateActivities", activaties.dateActivities)
            .get()
            .await()
        if (activatieQuery.documents.isNotEmpty()){
            for (document in activatieQuery){
                activatieCollection.document(document.id).delete().await()
            }
        } else {
            throw error("no Activatie by that Name")
        }

    }
    fun getActivaties(completion: (List<Activities>?, Exception?) -> Unit) {
        activatieCollection
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