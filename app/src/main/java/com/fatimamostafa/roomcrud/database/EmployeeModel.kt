package com.fatimamostafa.roomcrud.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmployeeModel(
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "firstName") val firstName: String = "",
    @ColumnInfo(name = "lastName") val lastName: String = "",
    @SerializedName("age") val age: Int = 0,
    @SerializedName("gender") val gender: String = "",
    @ColumnInfo(name = "imageUrl") val imageUrl: String = ""
) : Parcelable