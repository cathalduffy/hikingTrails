package org.wit.hikingtrails.models

interface HikeStore {
    suspend fun findAll(): List<HikeModel>
    suspend fun create(hike: HikeModel)
    suspend fun findById(id: Long) : HikeModel?
    suspend fun update(hike: HikeModel)
    suspend fun remove(hike: HikeModel)
}