package com.example.hapkidoplanningapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "activatie")
class Activities(
    var id: Int? = 1,

    @PrimaryKey
    var dateActivities: Date? = Date(),
    var title: String? = "no title",
    var description: String? = "no description ",
    var attendees: MutableList<User>? = mutableListOf(),
//    var trainer: User? = User("jhon", "dow"),
    var place: String? = " no place"
) {

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