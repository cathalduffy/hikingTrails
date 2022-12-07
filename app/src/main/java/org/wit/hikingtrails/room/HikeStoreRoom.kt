package org.wit.hikingtrails.room

import android.content.Context
import androidx.room.Room
import org.wit.hikingtrails.models.HikeModel
import org.wit.hikingtrails.models.HikeStore

class HikeStoreRoom(val context: Context) : HikeStore {

    var dao: HikeDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.hikeDao()
    }

    override suspend fun findAll(): List<HikeModel> {

        return dao.findAll()
    }

    override suspend fun findById(id: Long): HikeModel? {
        return dao.findById(id)
    }

    override suspend fun create(hike: HikeModel) {
        dao.create(hike)
    }

    override suspend fun update(hike: HikeModel) {
        dao.update(hike)
    }

    override suspend fun remove(hike: HikeModel) {
        dao.deleteHike(hike)
    }

    override suspend fun clear(){
    }
}