package org.wit.hikingtrails.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HikeModel(var id: Long = 0,
                          var name: String = "",
                          var description: String = "") : Parcelable