package com.example.hapkidoplanningapp.domain

import androidx.room.Entity
import java.io.Serializable

@Entity
class User(

    var name: String,
    var password: String,
    var belt: String? = "keub 8",
    var isTrainer: Boolean? = false


): Serializable {

}
