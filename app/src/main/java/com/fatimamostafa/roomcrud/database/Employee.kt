package com.fatimamostafa.roomcrud.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "employees")
@Parcelize
data class Employee(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @SerializedName("first") val firstName: String = "",
    @SerializedName("last") val lastName: String = "",
    @SerializedName("age") val age: Int = 0,
    @SerializedName("gender") val gender: String = "",
    @SerializedName("image_url") val imageUrl: String = ""
) : Parcelable