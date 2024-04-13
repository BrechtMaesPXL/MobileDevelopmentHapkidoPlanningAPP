package com.example.hapkidoplanningapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "activatie")
class Activities(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    var dateActivities: Date? = null,
    var title: String? = null,
    var description: String? = null,
    var attendees: MutableList<User>? = mutableListOf(),
    var trainer: User? = null,
    var place: String? = null
) {
    constructor() : this(null, null, null, null)

    fun addAttendee(users: User) {
        attendees?.add(users)
    }

    fun removeAttendee(name: String) {
        attendees?.removeIf { it.name == name }
    }
//    constructor(
//        id: Int,
//        dateActivities: Date,
//        title: String,
//        description: String,
//        place: String
//    ) : this(id, dateActivities, title, description, mutableListOf<User>(), place)
}