package com.example.hapkidoplanningapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var password: String,
    var belt: String,
    var isTrainer: Boolean
) {

}
