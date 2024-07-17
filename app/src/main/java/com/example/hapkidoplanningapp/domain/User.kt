package com.example.hapkidoplanningapp.domain

import androidx.room.Entity
import com.example.hapkidoplanningapp.beltGrade

@Entity
data class User(
    var eMail: String = "",
    var password: String = "",
    var name: String = "",
    var belt: beltGrade = beltGrade.Keub8 ,
    var isTrainer: Boolean = false
) {
    // No-argument constructor for Firestore
    constructor() : this("", "", "", beltGrade.Keub8, false)
}