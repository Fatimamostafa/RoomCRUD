package com.fatimamostafa.roomcrud.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmployeeModel(
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "firstName") val firstName: String = "",
    @ColumnInfo(name = "lastName") val lastName: String = "",
    @ColumnInfo(name = "age") val age: Int = 0,
    @ColumnInfo(name = "imageUrl") val imageUrl: String = "",
    @ColumnInfo(name = "gender") val gender: String = ""
) : Parcelable