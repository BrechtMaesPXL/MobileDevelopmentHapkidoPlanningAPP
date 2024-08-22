package com.example.hapkidoplanningapp.service

import android.content.ContentValues.TAG
import android.util.Log
import com.example.hapkidoplanningapp.domain.Activities
import com.example.hapkidoplanningapp.domain.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.util.Date

class activatiesService {

    private val db = Firebase.firestore
    private val activatieCollection = db.collection("Activaties")

    fun addActivaties(date: Date, title: String, trainer: User?, description: String, place: String ) {
        val newActivities = Activities(
            dateActivities = date,
            title = title,
            description = description,
            trainer = trainer,
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
    suspend fun getActivityByDate(date: Date?): Activities? {
        return try {
            val activatieQuery = activatieCollection
                .whereEqualTo("dateActivities", date)
                .get()
                .await()

            if (activatieQuery.documents.isNotEmpty()) {
                val documentSnapshot = activatieQuery.documents.first()
                documentSnapshot.toObject(Activities::class.java)?.apply {
                    this.id = documentSnapshot.id
                }
            } else {
                null
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error getting activity by date", e)
            null
        }
    }
    suspend fun editActivatiesByDate( updatedActivity: Activities): Boolean {
        return try {
            val activity = getActivityByDate(updatedActivity.dateActivities)
            if (activity != null) {
                activatieCollection.document(activity.id).set(updatedActivity).await()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error updating activity by date", e)
            false
        }
    }
}