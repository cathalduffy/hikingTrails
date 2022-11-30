package org.wit.hikingtrails.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HikeModel(var id: Long = 0,
                     var name: String = "",
                     var description: String = "",
                     var image: Uri = Uri.EMPTY,
                     var lat : Double = 0.0,
                     var lng: Double = 0.0,
                     var zoom: Float = 0f,
                     var difficultyLevel: String = "N/A",
                     var distance: Int = 0) : Parcelable


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable