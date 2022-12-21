package org.wit.hikingtrails.models

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class HikeModel(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                     var fbId: String = "",
                     var name: String = "",
                     var description: String = "",
                     var image: String = "",
                     var lat : Double = 0.0,
                     var lng: Double = 0.0,
                     var zoom: Float = 0f,
                     var difficultyLevel: String = "N/A",
                     var favourite: Boolean = true,
                     var distance: Int = 0) : Parcelable


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable