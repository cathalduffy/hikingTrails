package org.wit.hikingtrails.models

interface HikeStore {
    fun findAll(): List<HikeModel>
    fun create(hike: HikeModel)
    fun update(hike: HikeModel)
}