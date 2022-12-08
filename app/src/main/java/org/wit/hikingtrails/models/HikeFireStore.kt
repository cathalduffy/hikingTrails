package org.wit.hikingtrails.models

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HikeFireStore(val context: Context) : HikeStore {
    val hikes = ArrayList<HikeModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override suspend fun findAll(): List<HikeModel> {
        return hikes
    }

    override suspend fun findById(id: Long): HikeModel? {
        val foundHike: HikeModel? = hikes.find { p -> p.id == id }
        return foundHike
    }

    override suspend fun create(hike: HikeModel) {
        val key = db.child("users").child(userId).child("hikes").push().key
        key?.let {
            hike.fbId = key
            hikes.add(hike)
            db.child("users").child(userId).child("hikes").child(key).setValue(hike)
        }
    }

    override suspend fun update(hike: HikeModel) {
        var foundHike: HikeModel? = hikes.find { p -> p.fbId == hike.fbId }
        if (foundHike != null) {
            foundHike.name = hike.name
            foundHike.description = hike.description
            foundHike.image = hike.image
            foundHike.lat = hike.lat
            foundHike.lng = hike.lng
            foundHike.zoom = hike.zoom
            foundHike.distance = hike.distance
            foundHike.difficultyLevel = hike.difficultyLevel
        }

        db.child("users").child(userId).child("hikes").child(hike.fbId).setValue(hike)

    }

    override suspend fun remove(hike: HikeModel) {
        db.child("users").child(userId).child("hikes").child(hike.fbId).removeValue()
        hikes.remove(hike)
    }

    override suspend fun clear() {
        hikes.clear()
    }

    fun fetchHikes(hikesReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(hikes) {
                    it.getValue<HikeModel>(
                        HikeModel::class.java
                    )
                }
                hikesReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance("https://kotlinapp-b6e31-default-rtdb.firebaseio.com").reference
        hikes.clear()
        db.child("users").child(userId).child("hikes")
            .addListenerForSingleValueEvent(valueEventListener)
    }
}