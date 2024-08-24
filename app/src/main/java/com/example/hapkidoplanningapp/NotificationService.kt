package com.example.hapkidoplanningapp
import com.example.hapkidoplanningapp.data.NotificationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService {
    @POST("sendNotification") // Replace with your actual endpoint
    fun sendNotification(@Body notificationRequest: NotificationRequest): Call<Void>
}