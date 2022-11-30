package org.wit.hikingtrails.room

import androidx.room.*
import org.wit.hikingtrails.models.HikeModel

@Dao
interface HikeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(hike: HikeModel)

    @Query("SELECT * FROM HikeModel")
    fun findAll(): List<HikeModel>

    @Query("select * from HikeModel where id = :id")
    fun findById(id: Long): HikeModel

    @Update
    fun update(hike: HikeModel)

    @Delete
    fun deleteHike(hike: HikeModel)
}