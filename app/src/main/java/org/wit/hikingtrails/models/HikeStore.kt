package org.wit.hikingtrails.models

interface HikeStore {
    fun findAll(): List<HikeModel>
    fun create(hike: HikeModel)
    fun findById(id: Long) : HikeModel?
    fun update(hike: HikeModel)
}