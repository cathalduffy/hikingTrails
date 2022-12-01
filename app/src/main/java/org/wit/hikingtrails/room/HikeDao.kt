package org.wit.hikingtrails.room

import androidx.room.*
import org.wit.hikingtrails.models.HikeModel

@Dao
interface HikeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(hike: HikeModel)

    @Query("SELECT * FROM HikeModel")
    suspend fun findAll(): List<HikeModel>

    @Query("select * from HikeModel where id = :id")
    suspend fun findById(id: Long): HikeModel

    @Update
    suspend fun update(hike: HikeModel)

    @Delete
    suspend fun deleteHike(hike: HikeModel)
}