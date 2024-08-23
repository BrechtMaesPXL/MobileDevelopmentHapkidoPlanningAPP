package com.example.hapkidoplanningapp.domain

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.example.hapkidoplanningapp.beltGrade

@Entity
data class User(
    var eMail: String = "",
    var password: String = "",
    var name: String = "",
    var belt: beltGrade = beltGrade.Keub8,
    var isTrainer: Boolean = false
) : Parcelable {
    constructor() : this("", "", "", beltGrade.Keub8, false)

    // Constructor for Parcelable
    private constructor(parcel: Parcel) : this(
        eMail = parcel.readString() ?: "",
        password = parcel.readString() ?: "",
        name = parcel.readString() ?: "",
        belt = beltGrade.values()[parcel.readInt()],
        isTrainer = parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eMail)
        parcel.writeString(password)
        parcel.writeString(name)
        parcel.writeInt(belt.ordinal)
        parcel.writeByte(if (isTrainer) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}