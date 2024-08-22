    package com.example.hapkidoplanningapp.service

    import android.content.ContentValues
import android.util.Log
import com.example.hapkidoplanningapp.domain.Activities
import com.example.hapkidoplanningapp.domain.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

    class userService {
        private val db = Firebase.firestore
        private val userCollection = db.collection("Users")

        fun addUser(
            firebaseUser: FirebaseUser?,
            user: User) {

            if (firebaseUser != null) {
                userCollection
                    .document(firebaseUser.uid)
                    .set(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ")
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding user", e)
                    }
            }


        }
        suspend fun delActivaties(activaties: Activities) {
            val activatieQuery = userCollection
                .whereEqualTo("dateActivities", activaties.dateActivities)
                .get()
                .await()
            if (activatieQuery.documents.isNotEmpty()){
                for (document in activatieQuery){
                    userCollection.document(document.id).delete().await()
                }
            } else {
                throw error("no Activatie by that Name")
            }

        }
        suspend fun getUserByUID(uid: String): User? {
            return try {
                val documentSnapshot = userCollection.document(uid).get().await()

                if (documentSnapshot.exists()) {

                    documentSnapshot.toObject(User::class.java)
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.w(ContentValues.TAG, "Error getting user by UID", e)
                null
            }
        }
        suspend fun getAllUsers(): List<User>? {
            return try {
                val querySnapshot = userCollection
                    .orderBy("name", Query.Direction.ASCENDING)
                    .get()
                    .await()
                if (!querySnapshot.isEmpty) {
                    querySnapshot.documents.mapNotNull { it.toObject(User::class.java) }
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                Log.w(ContentValues.TAG, "Error getting all users", e)
                null
            }
        }

        suspend fun getUserByEmail(eMail: String): String? {
            return try {
                val userQuery = userCollection
                    .whereEqualTo("email", eMail)
                    .get()
                    .await()

                if (userQuery.documents.isNotEmpty()) {
                    val documentSnapshot = userQuery.documents.first()
                    documentSnapshot.id
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.w(ContentValues.TAG, "Error getting user by email", e)
                null
            }
        }

        suspend fun editUserByEmail(updatedUser: User): Boolean {
            return try {
                val existingUserId = getUserByEmail(updatedUser.eMail)
                if (existingUserId != null) {
                    userCollection.document(existingUserId).set(updatedUser).await()
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                Log.w(ContentValues.TAG, "Error updating user by email", e)
                false
            }
        }
    }