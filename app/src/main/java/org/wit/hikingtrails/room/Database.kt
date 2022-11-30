package org.wit.hikingtrails.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.wit.hikingtrails.helpers.Converters
import org.wit.hikingtrails.models.HikeModel

@Database(entities = arrayOf(HikeModel::class), version = 1,  exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun hikeDao(): HikeDao
}
