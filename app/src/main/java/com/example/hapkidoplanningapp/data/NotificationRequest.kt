package com.example.hapkidoplanningapp.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class NotificationRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("date") val date: Date // Use timestamp or other format as needed
)